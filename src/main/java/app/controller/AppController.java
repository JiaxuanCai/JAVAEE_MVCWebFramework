package app.controller;

import app.model.Book;
import app.service.AppService;
import app.service.BookService;
import mymvc.annotation.*;
import mymvc.model.MyModelView;
import org.apache.commons.fileupload.FileItem;

import java.io.File;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;


@MyController
@MyRequestMapping("/app")
public class AppController {


    @MyAutowired
    private AppService appService;
    @MyAutowired
    private BookService bookService;

    @MyRequestMapping(value = "/bookpage", method="POST")
    @ResponseView
    public MyModelView addBook(@MyRequestParam("id") String id, @MyRequestParam("title") String title, @MyRequestParam("author") String author){
        MyModelView mv = new MyModelView();
        bookService.addBook(Integer.parseInt(id), title, author);
        mv.setView("bookinfo");
        mv.addModel("bookList", bookService.getAllBooks());
        return mv;
    }

    @MyRequestMapping(value = "/bookpage", method = "GET")
    @ResponseView
    public MyModelView showBookPage(){
        MyModelView modelView = new MyModelView();
        List<Book> bookList = bookService.getAllBooks();
        modelView.setView("bookinfo");
        modelView.addModel("bookList", bookList);
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

        String path = "D:/code/JAVAEE_MVCWebFramework/temp/"; // 假定这里路径是固定的

        MyModelView mv = new MyModelView();
        mv.setView("upload_result");
        if (appService.uploadFile(source, path)){
            mv.addModel("info", "Successfully upload");
        }else{
            mv.addModel("info", "Failure to upload");
        }
        return mv;
    }
}
