package cn.glor.xiaolun.app.entity;

import javax.persistence.*;

/**
 * Created by heylear on 15/10/31.
 */
@Entity
@Table(name = "tt_attach", schema = "", catalog = "xlap")
public class AttachEntity extends BaseEntity{

    @Column(name = "attach_name")
    String attachName;

    @Column(name = "attach_type")
    String attachType;

    @Column(name = "attach_uri")
    String attachUri;

    @Column(name = "attach_note")
    String attachNote;

    public String getAttachName() {
        return attachName;
    }

    public void setAttachName(String attachName) {
        this.attachName = attachName;
    }

    public String getAttachType() {
        return attachType;
    }

    public void setAttachType(String attachType) {
        this.attachType = attachType;
    }

    public String getAttachUri() {
        return attachUri;
    }

    public void setAttachUri(String attachUri) {
        this.attachUri = attachUri;
    }

    public void setAttachNote(String attachNote) {
        this.attachNote = attachNote;
    }

    public String getAttachNote() {
        return attachNote;
    }
}
