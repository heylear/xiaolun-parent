package cn.glor.xiaolun.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by heylear on 15/10/31.
 */
@Entity
@Table(name = "tr_car_pic", schema = "", catalog = "xlap")
public class CarPicEntity extends BaseEntity {

    @Column(name = "mem_user_id")
    Long memUserId;

    @Column(name = "attach_id")
    Long attachId;

    public void setMemUserId(Long memUserId) {
        this.memUserId = memUserId;
    }

    public Long getMemUserId() {
        return memUserId;
    }

    public void setAttachId(Long attachId) {
        this.attachId = attachId;
    }

    public Long getAttachId() {
        return attachId;
    }
}
