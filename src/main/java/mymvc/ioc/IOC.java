package mymvc.ioc;

import mymvc.annotation.MyAutowired;
import mymvc.annotation.MyController;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.*;

public class IOC {
    private Map<String, Object> beanMap = null;

    public IOC(String packageName) throws Exception {
        beanMap = new HashMap<>();
        loadClass(packageName);
        initAttris();
    }

    private void loadClass(String packageName) {
        URL url = this.getClass().getClassLoader().getResource(packageName);
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

    private void initAttris() throws Exception {
        for (String name : beanMap.keySet()) {
            System.out.println("key=" + name + " value=" + beanMap.get(name));
            // 依赖注入
            attrAssign(beanMap.get(name));
        }
    }

    private void addBean(String beanName, Object bean){
        beanMap.put(beanName, bean);
    }


    public void attrAssign(Object object) throws Exception {
        // 1.使用反射机制获取当前类的所有属性
        Field[] declaredFields = object.getClass().getDeclaredFields();
        // 2.判断当前类是否存在注解
        for (Field field : declaredFields) {
            MyAutowired annotation = field.getAnnotation(MyAutowired.class);
            if (annotation != null) {
                // 获取属性名称
                String name = field.getName();
                // 根据beanName查找对象
                Object bean = getBean(name);
                // 3.默认使用属性名称,查找bean容器对象
                field.setAccessible(true);
                // 给属性赋值
                field.set(object, bean);
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

}