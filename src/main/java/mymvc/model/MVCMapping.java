package mymvc.model;

import mymvc.annotation.ResponseType;

import java.lang.reflect.Method;

public class MVCMapping {
    protected Method method;
    protected ResponseType responseType;
    protected Object cla;


    public MVCMapping(Method method, ResponseType responseType, Object cla){
        this.method = method;
        this.responseType = responseType;
        this.cla = cla;
    }

    public Method getMethod() {
        return method;
    }

    public ResponseType getResponseType() {
        return responseType;
    }

    public Object getCla() {
        return cla;
    }
}
