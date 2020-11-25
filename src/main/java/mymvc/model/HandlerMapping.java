package mymvc.model;

import mymvc.annotation.*;
import mymvc.ioc.IOC;
import mymvc.utils.UrlAndMethod;

import javax.servlet.ServletConfig;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;

public class HandlerMapping {

    private Properties properties = new Properties();

    private IOC ioc = null;

    private Map<UrlAndMethod, MVCMapping> handlerMapping = new HashMap<>();

    public HandlerMapping(ServletConfig config) {
        loadConfig(config);
        loadBeans();
        loadMapping();
    }

    public Map<UrlAndMethod, MVCMapping> getAllMappings(){
        return handlerMapping;
    }
    private void loadBeans(){
        try {
            ioc = new IOC(properties.getProperty("testModule"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void loadMapping()  {
//        System.out.println("load mapping");
        for(Map.Entry<String, Object> entry : ioc.getAllObject()){
            Class<?> cla = entry.getValue().getClass();

            String baseUrl = "";
            if(cla.isAnnotationPresent(MyRequestMapping.class)){
                baseUrl = cla.getAnnotation(MyRequestMapping.class).value();
            }

            Method[] methods = cla.getMethods();
            for(Method method : methods){
                if(!method.isAnnotationPresent(MyRequestMapping.class)){
                    continue;
                }
                String url = method.getAnnotation(MyRequestMapping.class).value();
                String requestMethod = method.getAnnotation(MyRequestMapping.class).method();
                if(method.isAnnotationPresent(ResponseBody.class)){
                    handlerMapping.put(new UrlAndMethod(baseUrl+url,requestMethod), new MVCMapping(method, ResponseType.Text, ioc.getBean(cla.getName())));
                }
                else if (method.isAnnotationPresent(ResponseView.class)){
                    handlerMapping.put(new UrlAndMethod(baseUrl+url,requestMethod), new MVCMapping(method, ResponseType.View, ioc.getBean(cla.getName())));
                }

            }

        }
    }


    private void loadConfig(ServletConfig config){
        String configLocation = config.getInitParameter("Configuration");
        InputStream resource = this.getClass().getClassLoader().getResourceAsStream(configLocation);
        try {
            properties.load(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            resource.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
