package cn.glor.xiaolun.app.repository.impl;

import cn.glor.xiaolun.app.entity.MpUserEntity;
import cn.glor.xiaolun.app.repository.MpUserRepository;
import cn.glor.xiaolun.app.util.DistanceUtil;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by heylear on 2015/10/24.
 */
@Repository
public class MpUserRepositoryImpl extends AbstractEntityRepository implements MpUserRepository {

    @Autowired
    public MpUserRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public MpUserEntity getMpUserByOpenId(String openId) {
        return queryUniqueEntity(MpUserEntity.class, Restrictions.eq("openId", openId));
    }

    @Override
    public MpUserEntity saveOrUpdateMpUser(WxMpUser wxMpUser) {
        MpUserEntity mpUserEntity = getMpUserByOpenId(wxMpUser.getOpenId());

        if (mpUserEntity == null) {
            mpUserEntity = new MpUserEntity();
        }

        mpUserEntity.setOpenId(wxMpUser.getOpenId());

        if (wxMpUser.getUnionId() != null)
            mpUserEntity.setUnionId(wxMpUser.getUnionId());

        mpUserEntity.setCountry(wxMpUser.getCountry());

        mpUserEntity.setCity(wxMpUser.getCity());

        mpUserEntity.setProvince(wxMpUser.getProvince());

        if (wxMpUser.getGroupId() != null)
            mpUserEntity.setGroupId(wxMpUser.getGroupId().toString());

        mpUserEntity.setHeadImgUrl(wxMpUser.getHeadImgUrl());

        if (wxMpUser.getLanguage() != null)
            mpUserEntity.setLanguage(wxMpUser.getLanguage());

        mpUserEntity.setNickname(wxMpUser.getNickname());

        if (wxMpUser.getRemark() != null)
            mpUserEntity.setRemark(wxMpUser.getRemark());

        mpUserEntity.setSex(wxMpUser.getSex());

        if (wxMpUser.getSubscribeTime() != null)
            mpUserEntity.setSubscribeTime(new Date(wxMpUser.getSubscribeTime() * 1000));

        if (wxMpUser.getSubscribe() != null)
            mpUserEntity.setSubscribe(wxMpUser.getSubscribe() ? "1" : "0");

        super.saveOrUpdate(mpUserEntity);

        return mpUserEntity;
    }

    @Override
    public MpUserEntity getMpUserByPhoneNumber(String phoneNumber) {
        return queryUniqueEntity(MpUserEntity.class, Restrictions.eq("phoneNumber", phoneNumber));
    }

    @Override
    public MpUserEntity getMpUserEntityById(long userId) {
        return queryUniqueEntity(MpUserEntity.class, Restrictions.eq("entityId", userId));
    }

    @Override
    public List<Map> getAllMpUserEntityBySquarePointMap(Map<String, double[]> squarePointMap) {
        String sql = "select a.entity_id, a.nickname, a.headimgurl, (select count(1) from tt_action where dest_user_id = a.entity_id) total_cnt " +
                "from tt_mp_user a inner join tt_location b on a.entity_id = b.mem_user_id" +
                " where a.entity_status = '1' and b.entity_status = '1' and exists(select 1 from tr_car_pic where mem_user_id = a.entity_id ) and b.cur_loc_x > ? and b.cur_loc_x < ? " +
                " and b.cur_loc_y > ? and b.cur_loc_y < ? order by total_cnt desc limit 50";
        double[] leftBottomPoint = squarePointMap.get(DistanceUtil.LEFT_BOTTOM_POINT);
        double[] rightTopPoint = squarePointMap.get(DistanceUtil.RIGHT_TOP_POINT);
        return queryMapListBySQL(sql, leftBottomPoint[0], rightTopPoint[0], leftBottomPoint[1], rightTopPoint[1]);

    }
}
