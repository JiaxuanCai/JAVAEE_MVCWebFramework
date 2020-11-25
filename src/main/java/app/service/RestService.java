package app.service;

import mymvc.annotation.MyService;

@MyService
public class RestService {
    private String name;


    public RestService() {
        this.name = "defaultName";
    }

    public String getBookById(int id){
        return "id: " + id;
    }
}
