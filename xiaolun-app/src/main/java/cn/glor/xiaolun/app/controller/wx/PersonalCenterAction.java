package cn.glor.xiaolun.app.controller.wx;

import cn.glor.xiaolun.app.controller.common.BaseAction;
import cn.glor.xiaolun.app.controller.common.SessionUtil;
import cn.glor.xiaolun.app.controller.common.beans.BaseVo;
import cn.glor.xiaolun.app.entity.AttachEntity;
import cn.glor.xiaolun.app.excep.BaseException;
import cn.glor.xiaolun.app.repository.ConfigRepository;
import cn.glor.xiaolun.app.service.PersonalCenterService;
import cn.glor.xiaolun.app.service.WxMpServiceHolder;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.FileInputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by caosh on 2015/10/24.
 */
@ParentPackage("wx")
@Result(name = "success", type = "json")
@InterceptorRef("personalCenter")
public class PersonalCenterAction extends BaseAction {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected BaseVo result = new BaseVo();

    @Autowired
    private WxMpServiceHolder wxMpServiceHolder;

    @Autowired
    private PersonalCenterService personalCenterService;

    @Autowired
    ConfigRepository configRepository;

    private String currentUrl;

    private long attachId;

    private String localId;

    private String serverId;

    private long destUserId;

    private String actType;

    private String questionText;

    private long baseQuestId;

    @Action("jsapiSignature")
    public String jsApiSignature() {

        try {
            result.setData(wxMpServiceHolder.getWxMpService().createJsapiSignature(currentUrl));
        } catch (Exception e) {
            result.setErrorCode(BaseVo.SYS_ERROR);
            result.setMessage(e.getMessage());
        }
        return SUCCESS;
    }

    @Action("uploadPic")
    public String uploadPic() {
        try {
            result.setData(personalCenterService.addCarPic(serverId, localId, SessionUtil.getSessionUserId()));
        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof BaseException) {
                result.setErrorCode(((BaseException) e).getErrCode());
            } else {
                result.setErrorCode(BaseVo.SYS_ERROR);
            }
            result.setMessage(e.getMessage());
        }
        return SUCCESS;
    }

    @Action("downloadPic")
    public String downloadPic() {
        try {
            if (attachId != 0) {
                AttachEntity attachEntity = personalCenterService.getAttachByAttachId(attachId);

                if (attachEntity != null) {

                    File file = new File(attachEntity.getAttachUri());

                    if (file.exists()) {
                        response.setContentType(attachEntity.getAttachType());
                        response.setHeader("Content-Disposition", "attachment;filename=" + attachEntity.getAttachName());
                        FileCopyUtils.copy(new FileInputStream(file), response.getOutputStream());
                        return null;
                    } else {
                        result.setData(attachEntity);
                    }

                } else {
                    result.setErrorCode(BaseVo.BUSSINESS_ERROR);
                    result.setMessage("找不到文件");
                }
            }
        } catch (Exception e) {
            if (e instanceof BaseException) {
                result.setErrorCode(((BaseException) e).getErrCode());
            } else {
                result.setErrorCode(BaseVo.SYS_ERROR);
            }
            result.setMessage(e.getMessage());
        }
        return SUCCESS;
    }

    @Action("deletePic")
    public String deletePic() {
        try {
            personalCenterService.delCarPic(attachId);
        } catch (Exception e) {
            if (e instanceof BaseException) {
                result.setErrorCode(((BaseException) e).getErrCode());
            } else {
                result.setErrorCode(BaseVo.SYS_ERROR);
            }
            result.setMessage(e.getMessage());
        }
        return SUCCESS;
    }

    @Action("mycenterall")
    public String mycenterall() {
        try {
            Map map = new HashMap();
            map.put("userid", SessionUtil.getSessionUser().getEntityId());
            map.put("headimgurl", SessionUtil.getSessionUser().getHeadImgUrl());
            map.put("piclist", personalCenterService.getAttachEntityListByUserId(SessionUtil.getSessionUserId()));
            map.put("actionlist", personalCenterService.getInteractionEntityList(SessionUtil.getSessionUserId()));
            map.put("actioncnt", personalCenterService.getActionCount(SessionUtil.getSessionUserId()));
            map.put("questlist", personalCenterService.getNonAnswerQuestionEntityList(SessionUtil.getSessionUserId()));
            result.setData(map);
        } catch (Exception e) {
            if (e instanceof BaseException) {
                result.setErrorCode(((BaseException) e).getErrCode());
            } else {
                result.setErrorCode(BaseVo.SYS_ERROR);
            }
            result.setMessage(e.getMessage());
        }
        return SUCCESS;
    }

    @Action("centerall")
    public String centerall() {
        try {
            Map map = new HashMap();
            map.put("destUser",personalCenterService.getMpUserById(destUserId));
            map.put("myheadimgurl",SessionUtil.getSessionUser().getHeadImgUrl());
            map.put("piclist", personalCenterService.getAttachEntityListByUserId(destUserId));
            map.put("actioncntdetail", personalCenterService.getActionCountDetail(destUserId));
            map.put("actioncnt", personalCenterService.getActionCount(destUserId));
            map.put("allquestlist", personalCenterService.getQuestionEntityList(destUserId));
            result.setData(map);
        } catch (Exception e) {
            if (e instanceof BaseException) {
                result.setErrorCode(((BaseException) e).getErrCode());
            } else {
                result.setErrorCode(BaseVo.SYS_ERROR);
            }
            result.setMessage(e.getMessage());
        }
        return SUCCESS;
    }

    @Action("interaction")
    public String interaction() {
        try {
            personalCenterService.addInteraction(SessionUtil.getSessionUserId(), destUserId, actType);
        } catch (Exception e) {
            if (e instanceof BaseException) {
                result.setErrorCode(((BaseException) e).getErrCode());
            } else {
                result.setErrorCode(BaseVo.SYS_ERROR);
            }
            result.setMessage(e.getMessage());
        }
        return SUCCESS;
    }

    @Action("actionlist")
    public String actionlist() {
        try {
            result.setData(personalCenterService.getInteractionEntityList(SessionUtil.getSessionUserId()));
        } catch (Exception e) {
            if (e instanceof BaseException) {
                result.setErrorCode(((BaseException) e).getErrCode());
            } else {
                result.setErrorCode(BaseVo.SYS_ERROR);
            }
            result.setMessage(e.getMessage());
        }
        return SUCCESS;
    }

    @Action("actioncount")
    public String actioncount() {
        try {
            result.setData(personalCenterService.getActionCount(SessionUtil.getSessionUserId()));
        } catch (Exception e) {
            if (e instanceof BaseException) {
                result.setErrorCode(((BaseException) e).getErrCode());
            } else {
                result.setErrorCode(BaseVo.SYS_ERROR);
            }
            result.setMessage(e.getMessage());
        }
        return SUCCESS;
    }

    @Action("askOrReply")
    public String askOrReply() {
        try {
            personalCenterService.replyQuestion(questionText, SessionUtil.getSessionUserId(), destUserId, baseQuestId);
        } catch (Exception e) {
            if (e instanceof BaseException) {
                result.setErrorCode(((BaseException) e).getErrCode());
            } else {
                result.setErrorCode(BaseVo.SYS_ERROR);
            }
            result.setMessage(e.getMessage());
        }
        return SUCCESS;
    }

    @Action("allQuestion")
    public String allQuestion() {
        try {
            Map map = new HashMap();
            map.put("allquestlist",personalCenterService.getQuestionEntityList(SessionUtil.getSessionUserId()));
            result.setData(map);
        } catch (Exception e) {
            if (e instanceof BaseException) {
                result.setErrorCode(((BaseException) e).getErrCode());
            } else {
                result.setErrorCode(BaseVo.SYS_ERROR);
            }
            result.setMessage(e.getMessage());
        }
        return SUCCESS;
    }

    @Action("nonAnswerQuestion")
    public String nonAnswerQuestion() {
        try {
            result.setData(personalCenterService.getNonAnswerQuestionEntityList(destUserId));
        } catch (Exception e) {
            if (e instanceof BaseException) {
                result.setErrorCode(((BaseException) e).getErrCode());
            } else {
                result.setErrorCode(BaseVo.SYS_ERROR);
            }
            result.setMessage(e.getMessage());
        }
        return SUCCESS;
    }

    @Action("nearbylist")
    public String test() {
        try {
            Map map = new HashMap();
            map.put("selfid", SessionUtil.getSessionUser().getEntityId());
            map.put("headimgurl", SessionUtil.getSessionUser().getHeadImgUrl());
            map.put("nearbylist", personalCenterService.getNearbyUserList(SessionUtil.getSessionUserId()));
            map.put("lastupdate",new Date());
            result.setData(map);
        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof BaseException) {
                result.setErrorCode(((BaseException) e).getErrCode());
            } else {
                result.setErrorCode(BaseVo.SYS_ERROR);
            }
            result.setMessage(e.getMessage());
        }
        return SUCCESS;
    }


    public BaseVo getResult() {
        return result;
    }

    public void setCurrentUrl(String currentUrl) {
        this.currentUrl = currentUrl;
    }

    public void setAttachId(long attachId) {
        this.attachId = attachId;
    }

    public void setLocalId(String localId) {
        this.localId = localId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public void setActType(String actType) {
        this.actType = actType;
    }

    public void setDestUserId(long destUserId) {
        this.destUserId = destUserId;
    }

    public void setBaseQuestId(long baseQuestId) {
        this.baseQuestId = baseQuestId;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }
}
