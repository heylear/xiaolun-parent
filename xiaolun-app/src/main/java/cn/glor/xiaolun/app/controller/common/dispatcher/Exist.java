package cn.glor.xiaolun.app.controller.common.dispatcher;

import com.opensymphony.xwork2.ActionContext;

/**
 * Created by caosh on 2015/10/30.
 */
public class Exist extends SessionCondition {

    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public boolean check() {
        return ActionContext.getContext().getSession().containsKey(key);
    }
}
