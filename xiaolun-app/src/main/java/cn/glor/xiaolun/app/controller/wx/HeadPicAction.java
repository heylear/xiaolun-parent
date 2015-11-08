package cn.glor.xiaolun.app.controller.wx;

import cn.glor.xiaolun.app.controller.common.BaseAction;
import cn.glor.xiaolun.app.controller.common.SessionKeys;
import cn.glor.xiaolun.app.entity.MpUserEntity;
import org.apache.struts2.convention.annotation.ParentPackage;

/**
 * Created by caosh on 2015/10/25.
 */
@ParentPackage("wx")
public class HeadPicAction extends BaseAction {

    private int size = 132;

    public void setSize(int size) {
        this.size = size;
    }

    public String getHeadPic() {
        MpUserEntity mpUserEntity = getLoggedMpUser();
        if (mpUserEntity == null) {
            return "";
        }

        String headPicLarge = mpUserEntity.getHeadImgUrl();
        if (size == 0) {
            return headPicLarge;
        }

        return headPicLarge.substring(0, headPicLarge.length() - 1) + size;
    }

}
