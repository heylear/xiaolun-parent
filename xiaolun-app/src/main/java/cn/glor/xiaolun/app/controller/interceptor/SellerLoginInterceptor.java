package cn.glor.xiaolun.app.controller.interceptor;

import cn.glor.xiaolun.app.controller.common.SessionKeys;
import cn.glor.xiaolun.app.controller.common.SessionUtil;
import cn.glor.xiaolun.app.entity.SellerAccountEntity;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.Locale;
import java.util.Map;

/**
 * Created by caosh on 2015/10/10.
 */
public class SellerLoginInterceptor implements Interceptor {

//    private static Log log = LogFactory.getLog(SellerLoginInterceptor.class);

    @Autowired
    private MessageSource messageSource;

    @Override
    public void init() {
    }

    @Override
    public void destroy() {
    }

    @Override
    public String intercept(ActionInvocation actionInvocation) throws Exception {
        if (!(SessionUtil.getSessionUser() instanceof SellerAccountEntity)) {
            throw new IllegalAccessException(messageSource.getMessage("seller.required", null, Locale.CHINA));
        }
        return actionInvocation.invoke();
    }
}
