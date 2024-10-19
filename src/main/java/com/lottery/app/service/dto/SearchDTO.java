package com.lottery.app.service.dto;

public class SearchDTO<T> {

    private int page;
    private int pageSize;
    private String sort; // column sort
    private String typeSort; // ASC | DESC
    private T data;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getTypeSort() {
        return typeSort;
    }

    public void setTypeSort(String typeSort) {
        this.typeSort = typeSort;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
