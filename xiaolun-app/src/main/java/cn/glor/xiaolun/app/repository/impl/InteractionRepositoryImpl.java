package cn.glor.xiaolun.app.repository.impl;

import cn.glor.xiaolun.app.repository.InteractionRepository;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by heylear on 2015/10/24.
 */
@Repository
public class InteractionRepositoryImpl extends AbstractEntityRepository implements InteractionRepository {

    @Autowired
    public InteractionRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public List<Map> getActionCountDetail(long userId) {
        String sql = "SELECT" +
                "  (SELECT count(1)" +
                "   FROM tt_action" +
                "   WHERE entity_status = '1' and act_type = '1' AND dest_user_id = ?)                          'sit_cnt'," +
                "  (SELECT count(1)" +
                "   FROM tt_action" +
                "   WHERE entity_status = '1' and act_type = '2' AND dest_user_id = ?)                          'try_cnt'," +
                "  (SELECT count(1)" +
                "   FROM tt_action" +
                "   WHERE entity_status = '1' and act_type = '2' AND dest_user_id = ?)                          'buy_cnt'";
        return queryMapListBySQL(sql, userId, userId, userId);
    }

    @Override
    public List<Map> getActionCount(long userId) {
        String sql = "SELECT" +
                "  (SELECT count(1)" +
                "   FROM tt_action" +
                "   WHERE entity_status = '1' AND dest_user_id = ?)                          total_cnt," +
                "  (SELECT count(1)" +
                "   FROM tt_action" +
                "   WHERE entity_status = '1' AND dest_user_id = ? AND" +
                "         date_format(create_time, '%Y%m%d') = date_format(now(), '%Y%m%d')) today_cnt";
        return queryMapListBySQL(sql, userId, userId);
    }

    @Override
    public List<Map> getInteractionListByUserId(long userId) {
        String sql = "SELECT" +
                "  a.headimgurl," +
                "  a.nickname," +
                "  a.entity_id," +
                "  b.act_user_id," +
                "  CASE WHEN b.act_type = '1'" +
                "    THEN '想坐坐你的车'" +
                "  WHEN b.act_type = '2'" +
                "    THEN '想试试你的车'" +
                "  WHEN b.act_type = '3'" +
                "    THEN '想买你的车' END act_name" +
                " FROM tt_mp_user a INNER JOIN tt_action b ON a.entity_id = b.act_user_id" +
                " WHERE b.dest_user_id = ? and a.entity_status = '1' and b.entity_status = '1'";
        return queryMapListBySQL(sql, userId);
    }
}
