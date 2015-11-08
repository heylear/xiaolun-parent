package cn.glor.xiaolun.app.controller.interceptor;

import cn.glor.xiaolun.app.controller.interceptor.exception.ExceptionValue;
import cn.glor.xiaolun.app.controller.interceptor.exception.ExceptionWrapper;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.ExceptionHolder;
import com.opensymphony.xwork2.interceptor.ExceptionMappingInterceptor;

/**
 * Created by caosh on 2015/10/19.
 */
public class ExceptionMappingJsonResultInterceptor extends ExceptionMappingInterceptor {

    @Override
    protected void publishException(ActionInvocation invocation, ExceptionHolder exceptionHolder) {
        invocation.getStack().push(new ExceptionWrapper(new ExceptionValue(exceptionHolder.getException())));
    }
}
