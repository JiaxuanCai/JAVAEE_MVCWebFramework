package mymvc.utils;

import java.util.Objects;

public class UrlAndMethod {
    String url;
    String method;

    public UrlAndMethod(String url, String method){
        this.url = url;
        this.method = method;
    }

    public String getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UrlAndMethod that = (UrlAndMethod) o;
        return Objects.equals(url, that.url) &&
                Objects.equals(method, that.method);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, method);
    }
}
