package cn.glor.xiaolun.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * Created by heylear on 15/10/31.
 */
@Entity
@Table(name = "tt_question", schema = "", catalog = "xlap")
public class QuestionEntity extends BaseEntity {

    @Lob
    @Column(name = "quest_text",columnDefinition = "TEXT")
    String questText;

    @Column(name = "parent_id")
    long parentId;

    @Column(name = "post_user_id")
    long postUserId;

    @Column(name = "dest_user_id")
    long destUserId;

    @Column(name = "quest_state")
    String questState;

    public String getQuestText() {
        return questText;
    }

    public void setQuestText(String questText) {
        this.questText = questText;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public long getPostUserId() {
        return postUserId;
    }

    public void setPostUserId(long postUserId) {
        this.postUserId = postUserId;
    }

    public long getDestUserId() {
        return destUserId;
    }

    public void setDestUserId(long destUserId) {
        this.destUserId = destUserId;
    }

    public void setQuestState(String questState) {
        this.questState = questState;
    }

    public String getQuestState() {
        return questState;
    }
}
