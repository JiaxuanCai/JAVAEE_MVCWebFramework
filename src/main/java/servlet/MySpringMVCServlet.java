package servlet;

import annotation.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Method;
import java.util.*;

import org.apache.commons.fileupload.FileItem;
import utils.HandlerMapping;
import utils.MVCMapping;
import utils.Uploadhandler;

public class MySpringMVCServlet extends HttpServlet {

//    private Properties properties = new Properties();
//
//    private List<String> classNames = new ArrayList<>();
//
////    private Map<String, Object> ioc = new HashMap<>();
//    private IOC ioc = new IOC();

//    private Map<String, MVCMapping> handlerMapping = new  HashMap<>();

//    private Map<String, Object> controllerMap  =new HashMap<>();
    Map<String, MVCMapping> handlerMapping;

    @Override
    public void init(ServletConfig config) {
        handlerMapping = new HandlerMapping(config).getAllMappings();
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


        MVCMapping mapping = handlerMapping.get(url);

        Method method = mapping.getMethod();
        ResponseType type = mapping.getResponseType();

        Class<?>[] paramTypes = method.getParameterTypes();
        Map<String, String[]> paramMap = req.getParameterMap();

        Object[] paramValues = new Object[paramTypes.length];



        for(int i=0; i<paramTypes.length; i++){
//            if(request)
            String requestParam = paramTypes[i].getSimpleName();
            if(requestParam.equals("FileItem")){
                paramValues[i] = fileItems.get(i);
                continue;
            }

            for(String[] param:paramMap.values()){
                String t = Arrays.toString(param);
                paramValues[i] = t;
                i++;
            }
        }
        try {
            String res = (String)method.invoke(mapping.getCla(), paramValues);

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
