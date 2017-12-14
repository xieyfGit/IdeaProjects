package com.yf.dao;

import java.util.List;

public interface BaseDao<T> {
    void save(T t);

    T get(int id);

    void del(int id);

    void edit(T t);

    T load(int id);

    List<T> findAll();

    int totalCount();

    PageModel<T> findByPager(int pageNo,int pageSize);

    void update(String sql);

    T findUnique(String sql);






}
