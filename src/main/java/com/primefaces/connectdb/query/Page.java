/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.primefaces.connectdb.query;


import java.util.List;
import static javax.faces.component.UIInput.isEmpty;

/**
 *
 * @author anonymous
 */
public class Page<T> {

    private Pagination pagination;
    private final long totalElements;
    private int currentPageSize;
    private List<T> contents;

    public Page(Pagination pagination, long totalElements) {
        this.pagination = pagination;
        this.totalElements = totalElements;
    }

    public int getCurrentPageNumber() {
        return pagination.getPageNumber();
    }

    public int getPageRequestSize() {
        return pagination.getPageSize();
    }

    public long getTotalElements() {
        return totalElements;
    }

    public int getCurrentPageSize() {
        return currentPageSize;
    }

    public List<T> getContents() {
        return contents;
    }

    public void setContents(List<T> contents) {
        if (isEmpty(contents)) {
            return;
        }

        this.contents = contents;
        this.currentPageSize = contents.size();
    }

    public int getTotalPages() {
        if (pagination.getPageSize() == 0) {
            return 0;
        }

        return (int) Math.ceil(totalElements / (float) pagination.getPageSize());
    }

    public boolean hasNext() {
        return pagination.getPageNumber() <= getTotalPages();
    }

    public Pagination nextPagination() {
        pagination = new Pagination(pagination.getPageNumber() + 1, pagination.getPageSize());
        return pagination;
    }

    public boolean hasPrev() {
        return pagination.getPageNumber() > 0;
    }

    public Pagination prevPagination() {
        pagination = new Pagination(pagination.getPageNumber() - 1, pagination.getPageSize());
        return pagination;
    }
}
