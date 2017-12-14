package com.yf.dao;

import com.yf.utils.SessionUtils;
import org.hibernate.Query;

import java.lang.reflect.ParameterizedType;
import java.util.List;

public class BaseDaoImpl<T> implements BaseDao<T> {

    private Class<T> clz;

    public BaseDaoImpl() {
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        this.clz = (Class<T>) type.getActualTypeArguments()[0];
    }

    @Override
    public void save(T t) {
        SessionUtils.getCurrentSession().save(t);
    }

    @Override
    public T get(int id) {
        return SessionUtils.getCurrentSession().get(clz, id);
    }

    @Override
    public void del(int id) {
        SessionUtils.getCurrentSession().delete(get(id));
    }

    @Override
    public void edit(T t) {
        SessionUtils.getCurrentSession().merge(t);
    }

    @Override
    public T load(int id) {
        return SessionUtils.getCurrentSession().load(clz, id);
    }

    @Override
    public List<T> findAll() {
        String hql = "from "+ clz.getSimpleName();
        Query query = SessionUtils.getCurrentSession().createQuery(hql);
        return query.list();
    }

    @Override
    public int totalCount() {
        String hql = "select count(t) from "+ clz.getSimpleName() + " t";
        Long temp = (Long) SessionUtils.getCurrentSession().createQuery(hql).uniqueResult();
        if (temp != null) {
            return temp.intValue();
        }
        return 0;
    }

    @Override
    public PageModel<T> findByPager(int pageNo, int pageSize) {
        PageModel<T> pageModel = new PageModel<>(pageNo,pageSize);
        String hql = "from "+ clz.getSimpleName();
        pageModel.setList(SessionUtils.getCurrentSession().createQuery(hql).setFirstResult((pageNo-1)*pageSize+1).setMaxResults(pageSize).list());
        pageModel.setRecordCount(totalCount());
        return pageModel;
    }

    @Override
    public void update(String sql) {
        SessionUtils.getCurrentSession().createSQLQuery(sql);
    }

    @Override
    public T findUnique(String sql) {
        return (T) SessionUtils.getCurrentSession().createSQLQuery(sql).uniqueResult();
    }
}
