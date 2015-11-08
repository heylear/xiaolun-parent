package cn.glor.xiaolun.app.controller.common.dispatcher;

import java.util.List;

/**
 * Created by caosh on 2015/10/30.
 */
public class Route {

    private String func;

    private String redirectUrl;

    private List<SessionCondition> sessionConditions;

    public String getFunc() {
        return func;
    }

    public void setFunc(String func) {
        this.func = func;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public List<SessionCondition> getSessionConditions() {
        return sessionConditions;
    }

    public void setSessionConditions(List<SessionCondition> sessionConditions) {
        this.sessionConditions = sessionConditions;
    }
}
