package mymvc.ioc;

import mymvc.annotation.MyAutowired;
import mymvc.annotation.MyController;
import mymvc.annotation.MyService;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.*;

public class IOC {
    private Map<String, Object> beanMap = null;

    public IOC(String packageName) throws Exception {
        beanMap = new HashMap<>();
        loadClass(packageName);
        doAutowire();
    }

    private void loadClass(String packageName) {
        URL url = this.getClass().getClassLoader().getResource("/"+packageName.replaceAll("\\.", "/"));
        System.out.println("in ioc url: " + url);
        File testModule = null;
        if (url != null) {
            testModule = new File(url.getFile());
            for(File file : Objects.requireNonNull(testModule.listFiles())){
                if(file.isDirectory()){
                    loadClass(packageName+"."+file.getName());
                }
                else {
                    String className = packageName +"."+ file.getName().replace(".class", "");
                    try {
                        Class<?> cla = Class.forName(className);
                        if(cla.isAnnotationPresent(MyController.class)){
                            addBean(cla.getName(), newInstance(cla));
                        }else if(cla.isAnnotationPresent(MyService.class)){
                            // service
                            MyService myService = cla.getAnnotation(MyService.class);
                            String beanName = myService.value();
                            if ("".equals(beanName.trim())){
                                beanName = toLowerFirstWord(cla.getSimpleName());
                            }

                            Object instance= cla.getDeclaredConstructor().newInstance();
                            addBean(beanName, instance);
                            System.out.println("add service bean " + beanName);

                            Class[] interfaces = cla.getInterfaces();
                            for (Class<?> i : interfaces){
                                addBean(i.getName(), instance);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        }

    }

    // 通过反射生成对象
    public Object newInstance(Class<?> classInfo) {
        try {
            return classInfo.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("fail to create object with reflect" + e.getMessage());
        }
    }

    private void doAutowire() throws Exception {
        for (Object o : beanMap.values()){
            attrAssign(o);
        }
    }

    private void addBean(String beanName, Object bean){
        beanMap.put(beanName, bean);
    }


    public void attrAssign(Object object) throws Exception {
        System.out.println("start to do autowired");
        // 1.使用反射机制获取当前类的所有属性
        Field[] declaredFields = object.getClass().getDeclaredFields();
        // 2.判断当前类是否存在注解
        for (Field field : declaredFields) {
            if (field.isAnnotationPresent(MyAutowired.class)){
                MyAutowired annotation = field.getAnnotation(MyAutowired.class);
                // 获取属性名称
                String name = field.getName();

                // 根据beanName查找对象
                Object bean = getBean(name);
                System.out.println("autowiring name " + name + " bean " + bean.toString());
                // 3.默认使用属性名称,查找bean容器对象
                field.setAccessible(true);
                // 给属性赋值
                field.set(object, bean);
                System.out.println("finish");
            }

        }
    }


    public Object getBean(String name){
        Object obj = beanMap.get(name);
        if(obj == null){
            throw new RuntimeException("No class named " + name);
        }
        return obj;
    }
    public Set<Map.Entry<String, Object>> getAllObject(){
        return beanMap.entrySet();
    }
    private String toLowerFirstWord(String name){

        char[] charArray = name.toCharArray();
        charArray[0] += 32;
        return String.valueOf(charArray);
    }

}