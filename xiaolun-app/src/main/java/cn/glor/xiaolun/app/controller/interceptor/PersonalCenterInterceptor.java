package cn.glor.xiaolun.app.controller.interceptor;

import cn.glor.xiaolun.app.controller.common.SessionKeys;
import cn.glor.xiaolun.app.controller.common.SessionUtil;
import cn.glor.xiaolun.app.controller.common.beans.BaseVo;
import cn.glor.xiaolun.app.entity.MpUserEntity;
import cn.glor.xiaolun.app.excep.PersonalCenterOperationException;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * Created by caosh on 2015/10/8.
 */
public class PersonalCenterInterceptor implements Interceptor {

	private static Log log = LogFactory.getLog(PersonalCenterInterceptor.class);

	public void init() {
	}

	public void destroy() {
	}

	public String intercept(ActionInvocation actionInvocation) throws Exception {
		log.info("intercept action: " + actionInvocation.getProxy().getActionName());
		if (SessionUtil.getSessionUserId() == 0) {
			Field field = actionInvocation.getAction().getClass().getDeclaredField("result");
			field.setAccessible(true);
			BaseVo baseVo = (BaseVo) field.get(actionInvocation.getAction());
			baseVo.setErrorCode(BaseVo.BUSSINESS_ERROR);
			baseVo.setMessage("登陆已过期，请重新从微信进入！");
			return Action.SUCCESS;
		}
		/*MpUserEntity mpUserEntity = new MpUserEntity();
		mpUserEntity.setEntityId(1l);
		mpUserEntity.setHeadImgUrl("http://wx.qlogo.cn/mmopen/PiajxSqBRaEKSAzszyvXtFw0NBkxDq43fn1JFMrJ4zU7l7O8hevdY3RuJRYb4SC9BSnTMHP0ZhExuDFATy0AvYQ/0");
		//SessionUtil.setSessionUser(mpUserEntity);
		actionInvocation.getInvocationContext().getSession().put(SessionKeys.SESSION_KEY_MEMBER_USER,mpUserEntity);*/
		return actionInvocation.invoke();
	}
}
