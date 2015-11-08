package cn.glor.xiaolun.app.repository;

import java.util.List;
import java.util.Map;

/**
 * Created by heylear on 15/10/31.
 */
public interface InteractionRepository extends BaseRepository {
    List<Map> getActionCount(long userId);
    List<Map> getActionCountDetail(long userId);
    List<Map> getInteractionListByUserId(long userId);
}
