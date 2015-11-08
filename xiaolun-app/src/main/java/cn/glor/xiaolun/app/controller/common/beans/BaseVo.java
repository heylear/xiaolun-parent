package cn.glor.xiaolun.app.controller.common.beans;

/**
 * Created by heylear on 15/11/3.
 */
public class BaseVo {

    public static final long SYS_ERROR = 500;

    public static final long BUSSINESS_ERROR = 999;

    long errorCode;
    String message;
    Object data;


    public long getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(long errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
