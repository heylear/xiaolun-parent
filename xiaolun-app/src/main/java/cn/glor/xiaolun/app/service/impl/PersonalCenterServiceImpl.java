package cn.glor.xiaolun.app.service.impl;

import cn.glor.xiaolun.app.controller.common.SessionUtil;
import cn.glor.xiaolun.app.entity.*;
import cn.glor.xiaolun.app.excep.PersonalCenterOperationException;
import cn.glor.xiaolun.app.repository.*;
import cn.glor.xiaolun.app.service.PersonalCenterService;
import cn.glor.xiaolun.app.util.DistanceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.activation.FileTypeMap;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by heylear on 15/10/31.
 */
@Service
public class PersonalCenterServiceImpl implements PersonalCenterService {

    @Autowired
    AttachRepository attachRepository;

    @Autowired
    CarPicRepository carPicRepository;

    @Autowired
    InteractionRepository interactionRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    ConfigRepository configRepository;

    @Autowired
    MpUserRepository mpUserRepository;

    @Override
    public long addCarPic(String serverId, String localId, long userId) throws PersonalCenterOperationException {
        AttachEntity attachEntity = new AttachEntity();
        attachEntity.setAttachName(serverId + ".jpg");
        attachEntity.setAttachType(FileTypeMap.getDefaultFileTypeMap().getContentType(attachEntity.getAttachName()));
        attachEntity.setAttachUri(localId);
        attachEntity.setAttachNote(serverId);

        attachRepository.saveOrUpdate(attachEntity);

        CarPicEntity carPicEntity = new CarPicEntity();
        carPicEntity.setAttachId(attachEntity.getEntityId());
        carPicEntity.setMemUserId(userId);

        carPicRepository.saveOrUpdate(carPicEntity);

        return attachEntity.getEntityId();
    }

    @Override
    public AttachEntity getAttachByAttachId(long attachId) throws PersonalCenterOperationException {
        return attachRepository.updateOrLoadAttachEntityById(attachId);
    }

    @Override
    public void delCarPic(long attachId) throws PersonalCenterOperationException {
        carPicRepository.deleteCarPicByAttachId(attachId);
    }

    @Override
    public List<AttachEntity> getAttachEntityListByUserId(long userId) throws PersonalCenterOperationException {
        return attachRepository.getAttachEntityListByUserId(userId);
    }

    @Override
    public void addInteraction(long actUserId, long destUserId, String actType) throws PersonalCenterOperationException {
        InteractionEntity interactionEntity = new InteractionEntity();
        interactionEntity.setActionType(actType);
        interactionEntity.setActUserId(actUserId);
        interactionEntity.setDestUserId(destUserId);
        interactionRepository.saveOrUpdate(interactionEntity);

    }

    @Override
    public List<Map> getInteractionEntityList(long userId) throws PersonalCenterOperationException {
        return interactionRepository.getInteractionListByUserId(userId);
    }

    @Override
    public List<Map> getNonAnswerQuestionEntityList(long userId) throws PersonalCenterOperationException {
        return questionRepository.getNonAnswerQuestionList(userId);
    }

    @Override
    public List<Map> getQuestionEntityList(long userId) throws PersonalCenterOperationException {

        List<Map> mapList = questionRepository.getAllQuestionList(userId);

        List<Map> newList = new LinkedList<>();
        for (Map map : mapList) {
            if (!Long.valueOf(0).equals(map.get("parent_id"))) {
                for (Map m : mapList) {
                    if (m.get("entity_id").equals(map.get("parent_id"))) {
                        m.put("reply", map);
                        newList.add(m);
                    }
                }
            }
        }

        if(userId != SessionUtil.getSessionUserId()){
            for (Map map : mapList){
                if (map.get("quest_state").equals("0")){
                    newList.add(map);
                }
            }
        }

        return newList;
    }

    @Override
    public List<Map> getActionCount(long userId) throws PersonalCenterOperationException {
        return interactionRepository.getActionCount(userId);
    }

    @Override
    public List<Map> getActionCountDetail(long userId) throws PersonalCenterOperationException {
        return interactionRepository.getActionCountDetail(userId);
    }

    @Override
    public void replyQuestion(String questionText, long askUserId, long destUserId, long baseQuestId) throws PersonalCenterOperationException {
        QuestionEntity questionEntity = new QuestionEntity();

        questionEntity.setPostUserId(askUserId);
        questionEntity.setDestUserId(destUserId);
        questionEntity.setParentId(baseQuestId);
        questionEntity.setQuestText(questionText);

        if (baseQuestId == 0) {
            questionEntity.setQuestState("0");
            questionRepository.saveOrUpdate(questionEntity);
        } else {
            questionEntity.setQuestState("1");
            questionRepository.saveReplyQuestion(questionEntity);
        }
    }

    @Override
    public List<Map> getNearbyUserList(long userId) throws PersonalCenterOperationException {
        LocationEntity lastestLocation = locationRepository.getLocationEntityByUserId(userId);

        Map<String, double[]> squarePointMap;
        if (lastestLocation != null) {
            squarePointMap = DistanceUtil.getSquarePointMap(lastestLocation.getCurLocX(),
                    lastestLocation.getCurLocY(), Double.valueOf(configRepository.getValue("loc.nearbyredius")));
        } else {
            //如果取不到当前用户的位置，则取默认位置
            squarePointMap = DistanceUtil.getSquarePointMap(Double.valueOf(configRepository.getValue("loc.defX")),
                    Double.valueOf(configRepository.getValue("loc.defY")),
                    Double.valueOf(configRepository.getValue("loc.nearbyredius")));
        }

        return mpUserRepository.getAllMpUserEntityBySquarePointMap(squarePointMap);
    }

    @Override
    public MpUserEntity getMpUserById(long userId) {
        return mpUserRepository.getMpUserEntityById(userId);
    }
}
