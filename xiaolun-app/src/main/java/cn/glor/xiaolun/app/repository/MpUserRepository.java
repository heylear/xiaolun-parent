package cn.glor.xiaolun.app.repository;

import cn.glor.xiaolun.app.entity.MpUserEntity;
import me.chanjar.weixin.mp.bean.result.WxMpUser;

import java.util.List;
import java.util.Map;

/**
 * Created by heylear on 15/10/31.
 */
public interface MpUserRepository extends BaseRepository {
    List<Map> getAllMpUserEntityBySquarePointMap(Map<String, double[]> squarePointMap);

    MpUserEntity saveOrUpdateMpUser(WxMpUser wxMpUser);

    MpUserEntity getMpUserByOpenId(String openId);

    MpUserEntity getMpUserByPhoneNumber(String phoneNumber);

    MpUserEntity getMpUserEntityById(long userId);
}
