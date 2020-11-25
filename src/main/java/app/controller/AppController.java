package app.controller;

import app.model.Book;
import mymvc.annotation.*;
import mymvc.model.MyModelView;
import org.apache.commons.fileupload.FileItem;

import java.io.File;


@MyController
@MyRequestMapping("/app")
public class AppController {


    @MyRequestMapping(value = "/bookpage", method = "GET")
    @ResponseView
    public MyModelView showBookPage(){
        MyModelView modelView = new MyModelView();
        Book book = new Book(2, "BookTitle", "frozewhale");
        modelView.setView("bookinfo");
        modelView.addModel("book", book);
        return modelView;
    }

    @MyRequestMapping(value = "/upload", method = "GET")
    @ResponseView
    public MyModelView upload_page() {
        MyModelView mv = new MyModelView();
        mv.setView("upload");
        return mv;
    }


    @MyRequestMapping(value = "/upload", method = "POST")
    @ResponseView

    public MyModelView upload(@MyRequestParam("file") FileItem source) {
        MyModelView mv = new MyModelView();
        mv.setView("upload_result");
        String fileName = source.getName();
        String path = "D:/code/JAVAEE_MVCWebFramework/temp/";
        File dest = new File(path + fileName);
        try {
            source.write(dest);
            mv.addModel("info", "Successfully upload");
            return mv;
        } catch (Exception e) {
            e.printStackTrace();
        }
        mv.addModel("info", "Failure to upload");
        return mv;
    }
}
