package cn.glor.xiaolun.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Created by heylear on 15/10/31.
 */
@Entity
@javax.persistence.Table(name = "tt_action", schema = "", catalog = "xlap")
public class InteractionEntity extends BaseEntity {

    @Column(name = "act_type")
    String actionType;

    @Column(name = "act_user_id")
    Long actUserId;

    @Column(name = "dest_user_id")
    Long destUserId;

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public Long getActUserId() {
        return actUserId;
    }

    public void setActUserId(Long actUserId) {
        this.actUserId = actUserId;
    }

    public Long getDestUserId() {
        return destUserId;
    }

    public void setDestUserId(Long destUserId) {
        this.destUserId = destUserId;
    }
}
