package cn.glor.xiaolun.app.repository.impl;

import cn.glor.xiaolun.app.controller.common.SessionUtil;
import cn.glor.xiaolun.app.entity.BaseEntity;
import cn.glor.xiaolun.app.repository.BaseRepository;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.ResultTransformer;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by heylear on 15/10/31.
 */
public abstract class AbstractEntityRepository extends HibernateDaoSupport implements BaseRepository {

    final static String ENTITY_VALID = "1";

    final static String ENTITY_INVALID = "0";

    protected AbstractEntityRepository(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }

    public void saveOrUpdate(BaseEntity entity) {

        if (entity.getEntityStatus() == null) {
            entity.setEntityStatus(ENTITY_VALID);
        }
        if (entity.getCreateTime() == null) {
            entity.setCreateTime(new Date());
        }
        if (entity.getCreateUser() == null) {
            entity.setCreateUser(SessionUtil.getSessionUserId());
        }
        if (entity.getUpdateUser() == null) {
            entity.setUpdateUser(SessionUtil.getSessionUserId());
        }
        entity.setUpdateTime(new Date());
        getSession().saveOrUpdate(entity);
    }

    public void deleteEntity(BaseEntity entity) {
        entity.setEntityStatus(ENTITY_INVALID);
        saveOrUpdate(entity);
    }

    protected <T> T queryUniqueEntity(Class<T> cls, Criterion... criterions) {
        Criteria criteria = getSession().createCriteria(cls);
        criteria.add(Restrictions.eq("entityStatus", ENTITY_VALID));
        if (criterions != null) {
            for (Criterion criterion : criterions) {
                criteria.add(criterion);
            }
        }
        return (T) criteria.uniqueResult();
    }

    protected <T> List<T> queryEntityList(Class<T> cls, Criterion... criterions) {
        Criteria criteria = getSession().createCriteria(cls);
        criteria.add(Restrictions.eq("entityStatus", ENTITY_VALID));
        if (criterions != null) {
            for (Criterion criterion : criterions) {
                criteria.add(criterion);
            }
        }
        return criteria.list();
    }

    protected <T> T queryUniqueEntityBySQL(String sql, Class<T> cls, Object... objects) {
        SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(cls);
        setSQLParameter(sqlQuery, objects);
        return (T) sqlQuery.uniqueResult();
    }

    protected <T> List<T> queryEntityListBySQL(String sql, Class<T> cls, Object... objects) {
        SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(cls);
        setSQLParameter(sqlQuery, objects);
        return sqlQuery.list();
    }

    /*protected  <T> T queryUniqueBeanBySQL(String sql, Class<T> cls, Object... objects) {
        SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(cls);
        setSQLParameter(sqlQuery,objects);
        sqlQuery.setResultTransformer(new BeanResultTransformer(cls));
        return (T) sqlQuery.uniqueResult();
    }

    protected <T> List<T> queryBeanListBySQL(String sql, Class<T> cls, Object... objects) {
        SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(cls);
        setSQLParameter(sqlQuery,objects);
        sqlQuery.setResultTransformer(new BeanResultTransformer(cls));
        return sqlQuery.list();
    }*/

    protected List<Map> queryMapListBySQL(String sql, Object... objects) {
        SQLQuery sqlQuery = getSession().createSQLQuery(sql);
        sqlQuery.setResultTransformer(new MapResultTransformer());
        setSQLParameter(sqlQuery, objects);
        return sqlQuery.list();
    }

    protected List<Map> queryUniqueMapBySQL(String sql, Object... objects) {
        SQLQuery sqlQuery = getSession().createSQLQuery(sql);
        sqlQuery.setResultTransformer(new MapResultTransformer());
        setSQLParameter(sqlQuery, objects);
        return sqlQuery.list();
    }

    private void setSQLParameter(Query query, Object... objects) {
        if (objects != null) {
            for (int i = 0; i < objects.length; i++) {
                logger.debug("Parameter" + (i + 1) + ": " + objects[i] + "; ");
                query.setParameter(i, objects[i]);
            }
        }
    }

    public static class MapResultTransformer implements ResultTransformer {
        @Override
        public Object transformTuple(Object[] values, String[] columns) {
            Map<String, Object> map = new HashMap<>();
            int i = 0;
            for (String column : columns) {
                map.put(column, values[i++]);
            }
            return map;
        }

        @Override
        public List transformList(List list) {
            return list;
        }
    }

    /*public static class BeanResultTransformer extends MapResultTransformer{

        Class<?> entityClass;

        public BeanResultTransformer(Class<?> entityClass){
            this.entityClass = entityClass;
        }

        @Override
        public Object transformTuple(Object[] values, String[] columns) {
            Map<String, Object> map = (Map<String, Object>) super.transformTuple(values,columns);

            Object result = null;
            try {
                result = entityClass.newInstance();
                Field[] fields = entityClass.getDeclaredFields();
                for (Field field: fields){
                    field.setAccessible(true);
                    Column column = field.getAnnotation(Column.class);
                    field.set(result, map.get(column.name()));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }
        @Override
        public List transformList(List list) {
            return list;
        }
    }*/
}
