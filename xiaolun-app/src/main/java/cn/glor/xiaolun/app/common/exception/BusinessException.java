package cn.glor.xiaolun.app.common.exception;

/**
 * Created by caosh on 2015/10/31.
 */
public class BusinessException extends Exception {

    private String message;

    public BusinessException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
