package cn.glor.xiaolun.app.repository;

import cn.glor.xiaolun.app.entity.QuestionEntity;

import java.util.List;
import java.util.Map;

/**
 * Created by heylear on 15/10/31.
 */
public interface QuestionRepository extends BaseRepository {
    List<Map> getNonAnswerQuestionList(long userId);

    List<Map> getAllQuestionList(long userId);

    void saveReplyQuestion(QuestionEntity questionEntity);
}
