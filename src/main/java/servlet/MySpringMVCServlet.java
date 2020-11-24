package servlet;

import annotation.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.file.Path;
import java.util.*;

import ioc.IOC;
import org.apache.commons.fileupload.FileItem;
import utils.MVCMapping;
import utils.Uploadhandler;

public class MySpringMVCServlet extends HttpServlet {

    private Properties properties = new Properties();

    private List<String> classNames = new ArrayList<>();

//    private Map<String, Object> ioc = new HashMap<>();
    private IOC ioc = new IOC();

    private Map<String, MVCMapping> handlerMapping = new  HashMap<>();

    private Map<String, Object> controllerMap  =new HashMap<>();


    @Override
    public void init(ServletConfig config) {
        loadConfig(config);
        loadClassNames(properties.getProperty("testModule"));
        loadClass();
        loadMapping();
    }

    private void loadMapping() {
        for(Map.Entry<String, Object> entry : ioc.getAllObject()){
            Class<?> cla = entry.getValue().getClass();

            String baseUrl = "";
            if(cla.isAnnotationPresent(MyRequestMapping.class)){
                baseUrl = cla.getAnnotation(MyRequestMapping.class).value();
            }

            Method[] methods = cla.getMethods();
            for(Method method : methods){
//                Annotation[] as = method.getAnnotations();
//                for (Annotation annotation : as) {
//                    System.out.println(annotation.toString());
//                }
                if(!method.isAnnotationPresent(MyRequestMapping.class)){
                    continue;
                }
                String url = method.getAnnotation(MyRequestMapping.class).value();
                if(method.isAnnotationPresent(ResponseBody.class)){
                    handlerMapping.put(baseUrl+url, new MVCMapping(method, ResponseType.Text));
                }
                else if (method.isAnnotationPresent(ResponseView.class)){
                    handlerMapping.put(baseUrl+url, new MVCMapping(method, ResponseType.View));
                }

                try {
                    controllerMap.put(baseUrl+url, cla.newInstance());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }

    private void loadClass() {
        for(String className : classNames){
            try {
                ioc.addObject(className);
//                Class<?> cla = Class.forName(className);
//                if(cla.isAnnotationPresent(MyController.class)){
//                    ioc.put(cla.getName(), cla.newInstance());
//                }
//                else{
//                    continue;
//                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void loadClassNames(String packageName) {
        URL url = this.getClass().getClassLoader().getResource(packageName);
        File testModule = new File(url.getFile());
        for(File file : testModule.listFiles()){
            if(file.isDirectory()){
                loadClassNames(packageName+"."+file.getName());
            }
            else {
                String className = packageName +"."+ file.getName().replace(".class", "");
                classNames.add(className);
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


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doDispatch(req, resp);
    }

    private void doDispatch(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//        System.out.println(req.getContextPath());
        String url = req.getRequestURI().replace(req.getContextPath(), "").replace("/temp","");
        if(!handlerMapping.containsKey(url)){
//            resp.sendRedirect("/upload.jsp");
//            return;
            resp.getWriter().write("404 Not Found!");
        }



        List<FileItem> fileItems = null;

        try{
             fileItems = Uploadhandler.getAllFiles(req);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        if(fileItems != null){
//            for(FileItem fileItem:fileItems){
//
//            }
//        }


        MVCMapping mapping = handlerMapping.get(url);

        Method method = mapping.getMethod();
        ResponseType type = mapping.getResponseType();

        Class<?>[] paramTypes = method.getParameterTypes();
//        Map<String, String[]> paramMap = req.getParameterMap();

        Object[] paramValues = new Object[paramTypes.length];



        for(int i=0; i<paramTypes.length; i++){
//            if(request)
            String requestParam = paramTypes[i].getSimpleName();
            if(requestParam.equals("FileItem")){
                paramValues[i] = fileItems.get(i);
                continue;
            }

//            for(String[] param:paramMap.values()){
//                String t = Arrays.toString(param);
//                paramValues[i] = t;
//                i++;
//            }
        }
        try {
            String res = (String)method.invoke(controllerMap.get(url), paramValues);

            switch (type) {
                case Text:
                    //把方法的执行结果以流的方式返回给前台
                    resp.getWriter().write(res);
                    break;
                case View:
                    req.getRequestDispatcher("/WEB-INF/"+res+".jsp").forward(req, resp);
//                    resp.sendRedirect(resresult);
                    break;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
