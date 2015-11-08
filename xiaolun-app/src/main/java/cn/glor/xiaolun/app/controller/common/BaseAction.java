package cn.glor.xiaolun.app.controller.common;

import cn.glor.xiaolun.app.entity.MpUserEntity;
import cn.glor.xiaolun.app.entity.SellerAccountEntity;
import cn.glor.xiaolun.app.repository.MpUserRepository;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by caosh on 2015/10/8.
 */
public abstract class BaseAction extends ActionSupport implements SessionAware, ServletRequestAware, ServletResponseAware {
	protected final Logger log = Logger.getLogger(this.getClass());

	private Map<String, Object> session;

	protected HttpServletRequest request;

	protected HttpServletResponse response;

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	protected void putSession(String key, Object value) {
		session.put(key, value);
	}

	protected Object getSession(String key) {
		return session.get(key);
	}

	protected boolean containsSessionKey(String key) {
		return session.containsKey(key);
	}

	protected MpUserEntity getLoggedMpUser() {
		return (MpUserEntity) getSession(SessionKeys.SESSION_KEY_MEMBER_USER);
	}

	protected void loginSeller(SellerAccountEntity sellerAccount) {
		putSession(SessionKeys.SESSION_KEY_MEMBER_USER, sellerAccount);
	}

	@Override
	public void setServletRequest(HttpServletRequest httpServletRequest) {
		this.request = httpServletRequest;
	}

	@Override
	public void setServletResponse(HttpServletResponse httpServletResponse) {
		this.response = httpServletResponse;
	}
}
