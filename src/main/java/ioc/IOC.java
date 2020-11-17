package ioc;

import annotation.MyController;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class IOC {
    private Map<String, Object> ioc = new HashMap<>();

    public void addObject(String className) throws Exception {
        Class<?> cla = Class.forName(className);
        if(cla.isAnnotationPresent(MyController.class)){
            ioc.put(cla.getName(), cla.newInstance());
        }
    }

    public Set<Map.Entry<String, Object>> getAllObject(){
        return ioc.entrySet();
    }

}
