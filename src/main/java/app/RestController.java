package app;

import app.model.Book;
import mymvc.annotation.MyController;
import mymvc.annotation.MyRequestMapping;
import mymvc.annotation.MyRequestParam;
import mymvc.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
@MyController
@MyRequestMapping("/api")
public class RestController {

    static List<Book> allBookList = new ArrayList<Book>(){{
        add(new Book(1, "JavaBook1", "Tian Runze"));
        add(new Book(2, "JavaBook2", "Jiang Dadong"));
        add(new Book(3, "JavaBook3", "Cai Jiaxuan"));
    }};

    @MyRequestMapping(value = "/book", method = "GET")
    @ResponseBody
    public Book getBook(@MyRequestParam("id") String sid){
        int id = Integer.parseInt(sid);
        Book book = getBookById(id);
        return book;
    }

    @MyRequestMapping(value = "/book", method = "POST")
    @ResponseBody
    public Book addBook(@MyRequestParam("id") String id, @MyRequestParam("title") String title, @MyRequestParam("author") String author){
        System.out.println(id);
        System.out.println(title);
        Book book = new Book(Integer.parseInt(id), title, author);
        allBookList.add(book);
        return book;
    }

    @MyRequestMapping(value = "/book/all", method = "GET")
    @ResponseBody
    public List<Book> getAllBooks(){
        return allBookList;
    }

    private Book getBookById(int id){
        for (Book book : allBookList){
            if (book.getId() == id){
                return book;
            }
        }
        return null;
    }
}
