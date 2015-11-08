package cn.glor.xiaolun.app.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by heylear on 15/10/31.
 */
@MappedSuperclass
public class BaseEntity {

    @Id
    @Column(name = "entity_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long entityId;

    @Column(name = "entity_status")
    String entityStatus;

    @Column(name = "create_time")
    Date createTime;

    @Column(name = "create_user")
    Long createUser;

    @Column(name = "update_time")
    Date updateTime;

    @Column(name = "update_user")
    Long updateUser;

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public Long getEntityId() {
        return entityId;
    }

    public String getEntityStatus() {
        return entityStatus;
    }

    public void setEntityStatus(String entityStatus) {
        this.entityStatus = entityStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Long updateUser) {
        this.updateUser = updateUser;
    }

    @Override
    public String toString() {
        return "BaseEntity{" +
                "entityId=" + entityId +
                ", entityStatus='" + entityStatus + '\'' +
                ", createTime=" + createTime +
                ", createUser=" + createUser +
                ", updateTime=" + updateTime +
                ", updateUser=" + updateUser +
                '}';
    }
}
