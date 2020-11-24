package app;

//import mymvc.annotation.GetMapping;
import app.model.Book;
import mymvc.annotation.*;
import org.apache.commons.fileupload.FileItem;

import java.io.File;


@MyController
@MyRequestMapping("/app")
public class AppController {

    @MyRequestMapping(value = "/get", method = "GET")
    @ResponseBody
    public String get(@MyRequestParam("p1") String p1, @MyRequestParam("p2") String p2){
        return "p1="+p1 + ",p2="+p2;
    }


    @MyRequestMapping(value = "/book", method = "GET")
    @ResponseBody
    public Book getBook(){
        return new Book(1, "Java编程", "Tian run ze");
    }


    @MyRequestMapping(value = "/book", method = "POST")
    @ResponseBody
    public Book addBook(@MyRequestParam("id") String id, @MyRequestParam("title") String title, @MyRequestParam("author") String author){
        System.out.println(id);
        System.out.println(title);
        Book book = new Book(Integer.parseInt(id), title, author);
        return book;
    }

//    @MyRequestMapping(value = "/bookpage", method = "GET")
//    @ResponseView
//    public Model showBookPage(){
//
//    }

    @MyRequestMapping(value = "/upload", method = "GET")
    @ResponseView
    public String upload_page() {
        return "upload";
    }


    @MyRequestMapping(value = "/upload", method = "POST")
    @ResponseBody
    public String upload(@MyRequestParam("file") FileItem source) {

        String fileName = source.getName();
        String path = "D:/code/JAVAEE_MVCWebFramework/temp/";
        File dest = new File(path + fileName);
        try {
            source.write(dest);
            return "Success!";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Failure!";
    }
}
