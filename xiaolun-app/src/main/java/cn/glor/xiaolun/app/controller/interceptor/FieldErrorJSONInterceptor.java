package cn.glor.xiaolun.app.controller.interceptor;

import cn.glor.xiaolun.app.controller.common.RestCodes;
import cn.glor.xiaolun.app.controller.interceptor.exception.ExceptionValue;
import cn.glor.xiaolun.app.controller.interceptor.exception.ExceptionWrapper;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ValidationAware;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.opensymphony.xwork2.interceptor.PreResultListener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;
import java.util.Map;

/**
 * Created by caosh on 2015/10/29.
 */
public class FieldErrorJSONInterceptor implements Interceptor {

    private static Log log = LogFactory.getLog(FieldErrorJSONInterceptor.class);

    @Override
    public void init() {
    }

    @Override
    public void destroy() {
    }

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        invocation.addPreResultListener(new PreResultListener() {
            @Override
            public void beforeResult(ActionInvocation invocation, String resultCode) {
                if ("input".equals(resultCode)) {
                    Object action = invocation.getAction();
                    if (action instanceof ValidationAware) {
                        ValidationAware validationAwareAction = (ValidationAware) action;

                        if (validationAwareAction.hasErrors()) {
                            for (Map.Entry<String, List<String>> fieldError : validationAwareAction.getFieldErrors().entrySet()) {
                                log.debug("输入校验失败：[" + fieldError.getKey() + "]" + fieldError.getValue().get(0));

                                invocation.getStack().push(new ExceptionWrapper(new ExceptionValue(
                                        RestCodes.VALIDATE_ERROR, /*"[" + fieldError.getKey() + "]" +*/ fieldError.getValue().get(0)
                                )));
                                invocation.setResultCode("exception");
                                break;
                            }
                        }
                    }
                }
            }
        });
        return invocation.invoke();
    }
}
