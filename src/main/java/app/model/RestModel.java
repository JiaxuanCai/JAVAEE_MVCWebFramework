package app.model;

public class RestModel {
    private int code;
    private String msg;
    private Object data;

    public RestModel() {

    }

    @Override
    public String toString() {
        return "RestModel{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", object=" + data +
                '}';
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public RestModel(int code, String msg, Object object) {
        this.code = code;
        this.msg = msg;
        this.data = object;
    }
}
