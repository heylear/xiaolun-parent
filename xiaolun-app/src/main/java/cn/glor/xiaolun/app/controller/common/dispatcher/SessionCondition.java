package cn.glor.xiaolun.app.controller.common.dispatcher;

/**
 * Created by caosh on 2015/10/30.
 */
public abstract class SessionCondition {

    private String redirectUrl;

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public abstract boolean check();
}
