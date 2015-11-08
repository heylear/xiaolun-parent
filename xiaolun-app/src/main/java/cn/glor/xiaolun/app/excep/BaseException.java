package cn.glor.xiaolun.app.excep;

import org.springframework.http.HttpStatus;

/**
 * Created by heylear on 15/10/31.
 */
public class BaseException extends Exception {

    long errCode = HttpStatus.BAD_GATEWAY.value();

    String message;

    public BaseException(String message){
        super(message);
        this.message = message;
    }

    public BaseException(long errCode, String message){
        super(message);
        this.errCode = errCode;
        this.message = message;
    }

    public long getErrCode() {
        return errCode;
    }

    public void setErrCode(long errCode) {
        this.errCode = errCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
