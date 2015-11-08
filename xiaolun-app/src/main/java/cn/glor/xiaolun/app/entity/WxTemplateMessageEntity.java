package cn.glor.xiaolun.app.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by caosh on 2015/10/30.
 */
@Entity
@Table(name = "tm_wx_tplmsg", schema = "", catalog = "xlap")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class WxTemplateMessageEntity extends BaseEntity {

    @Column(name = "tpl_name")
    private String tplName;

    @Column(name = "tpl_id")
    private String tplId;

    public String getTplName() {
        return tplName;
    }

    public void setTplName(String wtmiName) {
        this.tplName = wtmiName;
    }

    public String getTplId() {
        return tplId;
    }

    public void setTplId(String templateId) {
        this.tplId = templateId;
    }

    @Override
    public String toString() {
        return "WxTemplateMessageEntity{" +
                "tplName='" + tplName + '\'' +
                ", tplId='" + tplId + '\'' +
                '}';
    }
}
