package cn.glor.xiaolun.app.controller.interceptor.exception;

import cn.glor.xiaolun.app.common.exception.BusinessException;
import cn.glor.xiaolun.app.controller.common.RestCodes;
import org.hibernate.HibernateException;
import org.springframework.dao.DataAccessException;

/**
 * Created by caosh on 2015/10/19.
 */
public class ExceptionValue {

    private int errorCode;

    private String errorMessage;

    public ExceptionValue(Exception e) {
        e.printStackTrace();

        if (e instanceof BusinessException) {
            errorCode = RestCodes.BUSINESS_ERROR;
            errorMessage = e.getMessage();
        } else if (e instanceof HibernateException || e instanceof DataAccessException) {
            errorCode = RestCodes.DB_ERROR;
            errorMessage = "DB error";
        } else if (e instanceof RuntimeException) {
            errorCode = RestCodes.UNCHECKED_EXCEPTION;
            errorMessage = e.getMessage();
        } else {
            errorCode = RestCodes.CHECKED_EXCEPTION;
            errorMessage = e.getMessage();
        }
    }

    public ExceptionValue(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
