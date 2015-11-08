package cn.glor.xiaolun.app.controller.interceptor;

import cn.glor.xiaolun.app.controller.common.SessionKeys;
import cn.glor.xiaolun.app.controller.common.SessionUtil;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Created by caosh on 2015/10/8.
 */
public class WxInsideInterceptor implements Interceptor {

//	private static Log log = LogFactory.getLog(WxInsideInterceptor.class);

    @Autowired
    private MessageSource messageSource;

    public void init() {
    }

    public void destroy() {
    }

    public String intercept(ActionInvocation actionInvocation) throws Exception {
        if (SessionUtil.getSessionUser() == null) {
            throw new IllegalArgumentException(messageSource.getMessage("wx.required", null, Locale.CHINA));
        }
        return actionInvocation.invoke();
    }
}
