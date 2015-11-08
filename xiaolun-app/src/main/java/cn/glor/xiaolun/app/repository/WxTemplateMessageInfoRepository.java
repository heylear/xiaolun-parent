package cn.glor.xiaolun.app.repository;

import cn.glor.xiaolun.app.entity.WxTemplateMessageEntity;

/**
 * Created by caosh on 2015/10/30.
 */
public interface WxTemplateMessageInfoRepository {

    WxTemplateMessageEntity findByName(String name);
}
