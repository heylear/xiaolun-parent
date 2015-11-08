package cn.glor.xiaolun.app.controller.interceptor.exception;

/**
 * Created by caosh on 2015/10/19.
 */
public class ExceptionWrapper {

    private ExceptionValue exception;

    public ExceptionWrapper(ExceptionValue exception) {
        this.exception = exception;
    }

    public ExceptionValue getException() {
        return exception;
    }
}
