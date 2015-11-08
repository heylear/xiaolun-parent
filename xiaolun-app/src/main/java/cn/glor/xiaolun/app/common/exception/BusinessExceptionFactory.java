package cn.glor.xiaolun.app.common.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * Created by caosh on 2015/10/31.
 */
@Component
public class BusinessExceptionFactory {

    @Autowired
    private ResourceBundleMessageSource messageSource;

    public BusinessException ofKey(String key) {
        String message = messageSource.getMessage(key, null, Locale.CHINA);
        return new BusinessException(message);
    }
}
