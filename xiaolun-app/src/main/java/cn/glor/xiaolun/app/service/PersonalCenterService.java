package cn.glor.xiaolun.app.service;

import cn.glor.xiaolun.app.entity.AttachEntity;
import cn.glor.xiaolun.app.entity.MpUserEntity;
import cn.glor.xiaolun.app.excep.PersonalCenterOperationException;

import java.util.List;
import java.util.Map;

/**
 * Created by heylear on 15/10/31.
 */
public interface PersonalCenterService {

    /**
     * 添加车型图片
     *
     * @param serverId 微信服务器图片ID
     * @param localId  微信本地图片ID
     * @return 图片文件ID
     * @throws PersonalCenterOperationException
     */
    long addCarPic(String serverId, String localId, long userId) throws PersonalCenterOperationException;

    /**
     * 获取文件
     *
     * @param attachId
     * @throws PersonalCenterOperationException
     */
    AttachEntity getAttachByAttachId(long attachId) throws PersonalCenterOperationException;

    /**
     * 删除车型图片
     *
     * @param attachId
     * @throws PersonalCenterOperationException
     */
    void delCarPic(long attachId) throws PersonalCenterOperationException;

    /**
     * 获取车型图片列表
     *
     * @param userId
     * @return
     * @throws PersonalCenterOperationException
     */
    List<AttachEntity> getAttachEntityListByUserId(long userId) throws PersonalCenterOperationException;

    /**
     * 新增点赞动态
     *
     * @param actUserId  点赞人用户ID
     * @param destUserId 被点赞人用户ID
     * @param actType    点赞类型
     * @throws PersonalCenterOperationException
     */
    void addInteraction(long actUserId, long destUserId, String actType) throws PersonalCenterOperationException;

    /**
     * 获取某个用户的点赞列表
     *
     * @param userId
     * @return
     * @throws PersonalCenterOperationException
     */
    List<Map> getInteractionEntityList(long userId) throws PersonalCenterOperationException;

    /**
     * 获取某个用户的未回答问题列表
     *
     * @param userId
     * @return
     * @throws PersonalCenterOperationException
     */
    List<Map> getNonAnswerQuestionEntityList(long userId) throws PersonalCenterOperationException;

    /**
     * 获取某个用户的所有问题列表
     *
     * @param userId
     * @return
     * @throws PersonalCenterOperationException
     */
    List<Map> getQuestionEntityList(long userId) throws PersonalCenterOperationException;

    /**
     * 获取某个用户的动态统计
     *
     * @param userId
     * @return
     * @throws PersonalCenterOperationException
     */
    List<Map> getActionCount(long userId) throws PersonalCenterOperationException;

    /**
     * 获取某个用户的动态统计明细
     *
     * @param userId
     * @return
     * @throws PersonalCenterOperationException
     */
    List<Map> getActionCountDetail(long userId) throws PersonalCenterOperationException;

    /**
     * 回复问题
     *
     * @param questionText
     * @param askUserId
     * @param destUserId
     * @param baseQuestId
     * @throws PersonalCenterOperationException
     */
    void replyQuestion(String questionText, long askUserId, long destUserId, long baseQuestId) throws PersonalCenterOperationException;


    /**
     * 获取某个用户的周边用户列表
     *
     * @param userId
     * @throws PersonalCenterOperationException
     */
    List<Map> getNearbyUserList(long userId) throws PersonalCenterOperationException;

    MpUserEntity getMpUserById(long destUserId);
}
