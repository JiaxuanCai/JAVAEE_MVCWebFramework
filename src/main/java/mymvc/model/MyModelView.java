package mymvc.model;

import java.util.HashMap;
import java.util.Map;

public class MyModelView {

    private String view;
    private Map<String, Object> modelMap = new HashMap<>();

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public Map<String, Object> getModelMap() {
        return modelMap;
    }

    public void addModel(String name, Object attr){
        modelMap.put(name, attr);
    }

}
