package cn.glor.xiaolun.app.controller.common;

import cn.glor.xiaolun.app.entity.MpUserEntity;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpSession;

/**
 * Created by heylear on 15/10/31.
 */
public class SessionUtil {

    public static MpUserEntity getSessionUser(){
        try {
            return (MpUserEntity) getSession().getAttribute(SessionKeys.SESSION_KEY_MEMBER_USER);
        } catch (Exception e) {
            return null;
        }
    }

    public static void setSessionUser(MpUserEntity mpUserEntity){
        getSession().setAttribute(SessionKeys.SESSION_KEY_MEMBER_USER, mpUserEntity);
    }

    public static long getSessionUserId(){
        try {
            return getSessionUser().getEntityId();
        } catch (Exception e) {
            return 0l;
        }
    }

    protected static  HttpSession getSession(){
        HttpSession session = ServletActionContext.getRequest().getSession();
        if (session == null){
            throw new RuntimeException("can't init session context!");
        }
        return session;
    }

}
