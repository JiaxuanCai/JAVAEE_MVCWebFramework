package app.controller;

import app.model.Book;
import app.model.RestModel;
import app.service.BookService;
import mymvc.annotation.*;

import java.util.List;
@MyController
@MyRequestMapping("/api")
public class RestController {

    @MyAutowired
    private BookService bookService;


    @MyRequestMapping(value = "/book", method = "GET")
    @ResponseBody
    public Book getBook(@MyRequestParam("id") String sid){
        int id = Integer.parseInt(sid);
        return bookService.getBookById(id);
    }

    @MyRequestMapping(value = "/book", method = "POST")
    @ResponseBody
    public RestModel addBook(@MyRequestParam("id") String id, @MyRequestParam("title") String title, @MyRequestParam("author") String author){
        bookService.addBook(Integer.parseInt(id), title, author);
        return new RestModel(200, "ok", null);
    }

    @MyRequestMapping(value = "/book2", method = "GET")
    @ResponseBody
    public RestModel getBookMsg(@MyRequestParam("id") String sid){
        int id = Integer.parseInt(sid);
        Book book = bookService.getBookById(id);
        return new RestModel(200, "ok", book);
    }

    @MyRequestMapping(value = "/book/all", method = "GET")
    @ResponseBody
    public List<Book> getAllBooks(){
        return bookService.getAllBooks();
    }


}
