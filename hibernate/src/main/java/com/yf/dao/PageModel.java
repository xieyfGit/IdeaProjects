package com.yf.dao;

import java.util.List;

public class PageModel<T> {

    private int pageNo = 1;
    private int pageSize = 10;
    private int recordCount;
    private int pageCount;
    private List<T> list;

    public PageModel() {
    }

    public PageModel(int pageNo, int pageSize) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(int recordCount) {
        this.recordCount = recordCount;
    }

    public int getPageCount() {
        if (getRecordCount()<=0) {
            return 0;
        }
        int temp = recordCount%pageSize;
        return temp>0?recordCount/pageSize+1:recordCount/pageSize;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }



}
