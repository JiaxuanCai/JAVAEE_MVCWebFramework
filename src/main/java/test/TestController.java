package test;


import annotation.MyController;
import annotation.MyRequestMapping;
import annotation.MyRequestParam;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@MyController
@MyRequestMapping("/test")
public class TestController {

	 @MyRequestMapping("/doTest")
    public void test1(HttpServletRequest request, HttpServletResponse response,
    		@MyRequestParam("param") String param){
 		System.out.println(param);
	    try {
            response.getWriter().write( "doTest method success! param:"+param);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	 @MyRequestMapping("/doTest2")
    public void test2(HttpServletRequest request, HttpServletResponse response){
        try {
            response.getWriter().println("doTest2 method success!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @MyRequestMapping("/doTest3")
    public void test3(HttpServletRequest request, HttpServletResponse response,
                      @MyRequestParam("p1") String p1,
                      @MyRequestParam("p2") String p2){
        try {
            response.getWriter().println("doTest3 method success!" + p1 + " "+p2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
