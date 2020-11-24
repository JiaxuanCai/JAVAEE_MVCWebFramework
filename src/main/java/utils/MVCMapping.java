package utils;

import annotation.ResponseType;

import java.lang.reflect.Method;

public class MVCMapping {
    Method method;
    ResponseType responseType;

    public MVCMapping(Method method, ResponseType responseType){
        this.method = method;
        this.responseType = responseType;
    }

    public Method getMethod() {
        return method;
    }

    public ResponseType getResponseType() {
        return responseType;
    }
}
