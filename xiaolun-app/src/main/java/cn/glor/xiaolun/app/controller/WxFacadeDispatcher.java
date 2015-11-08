package cn.glor.xiaolun.app.controller;

import cn.glor.xiaolun.app.controller.common.dispatcher.Route;
import cn.glor.xiaolun.app.controller.common.dispatcher.SessionCondition;
import cn.glor.xiaolun.app.repository.ConfigRepository;
import me.chanjar.weixin.common.util.http.URIUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by caosh on 2015/10/8.
 */
public class WxFacadeDispatcher {

    private static final Pattern REDIRECT_STATE_PATTERN = Pattern.compile("([a-z]+)(Type(\\w+)Id(\\d+))?");

    private List<Route> routeList;

    @Autowired
    ConfigRepository configRepository;

    public void setRouteList(List<Route> routeList) {
        this.routeList = routeList;
    }

    public String dispatch(String funcArg) {
        // 解析func中的id参数，如func=customerTypeConsultId123，功能号为customer，type为Consult, id为123
        String func = null;
        String type = null;
        String id = null;
        Matcher m = REDIRECT_STATE_PATTERN.matcher(funcArg);

        if (m.find() && m.groupCount() == 4) {
            func = m.group(1);
            type = m.group(3);
            id = m.group(4);
        } else {
            func = funcArg;
        }

        String redirectUrl = getRedirectUrlFor(func);
        if (type != null && id != null) {
            redirectUrl = redirectUrl + "&type=" + type + "&id=" + id;
        }
        return redirectUrl;
    }

    private String getRedirectUrlFor(String func) {
        // state: 重定向后会带上state参数，开发者可以填写a-zA-Z0-9的参数值，最多128字节
        // http://mp.weixin.qq.com/wiki/17/c0f37d5704f0b64713d5d2c37b468d75.html
        for (Route route : routeList) {
            if (route.getFunc().equals(func)) {
                if (route.getSessionConditions() != null) {
                    for (SessionCondition sessionCondition : route.getSessionConditions()) {
                        if (sessionCondition.check()) {
                            return sessionCondition.getRedirectUrl();
                        }
                    }
                }
                return route.getRedirectUrl();
            }
        }
        throw new IllegalArgumentException("func = " + func);
    }
}
