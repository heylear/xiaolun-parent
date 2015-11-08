package cn.glor.xiaolun.app.repository.impl;

import cn.glor.xiaolun.app.entity.QuestionEntity;
import cn.glor.xiaolun.app.repository.QuestionRepository;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by heylear on 2015/10/24.
 */
@Repository
public class QuestionRepositoryImpl extends AbstractEntityRepository implements QuestionRepository {

    @Autowired
    public QuestionRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public List<Map> getNonAnswerQuestionList(long userId) {
        String sql = "SELECT" +
                "  a.post_user_id," +
                "  b.nickname," +
                "  b.headimgurl," +
                "  a.quest_text," +
                "  a.entity_id," +
                "  a.parent_id," +
                "  a.create_time" +
                " FROM tt_question a INNER JOIN tt_mp_user b ON a.post_user_id = b.entity_id" +
                " WHERE a.entity_status = '1' AND b.entity_status = '1' AND a.quest_state = '0' AND a.dest_user_id = ?";
        return queryMapListBySQL(sql, userId);
    }

    @Override
    public List<Map> getAllQuestionList(long userId) {
        String sql = "SELECT" +
                "  b.nickname," +
                "  a.post_user_id," +
                "  b.headimgurl," +
                "  a.quest_text," +
                "  a.entity_id," +
                "  a.parent_id," +
                "  a.quest_state," +
                "  a.create_time" +
                " FROM tt_question a INNER JOIN tt_mp_user b ON a.post_user_id = b.entity_id" +
                " WHERE a.entity_status = '1' AND b.entity_status = '1' AND  a.dest_user_id = ?";
        return queryMapListBySQL(sql, userId);
    }

    @Override
    public void saveReplyQuestion(QuestionEntity questionEntity) {
        super.saveOrUpdate(questionEntity);
        if(questionEntity.getParentId() != 0){
            QuestionEntity q = getHibernateTemplate().load(QuestionEntity.class, questionEntity.getParentId());
            if(q==null){
                throw new RuntimeException("问题不存在");
            }
            q.setQuestState("1");
            super.saveOrUpdate(q);
        }

    }
}
