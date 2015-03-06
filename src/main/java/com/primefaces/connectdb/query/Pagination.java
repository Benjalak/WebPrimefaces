/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.primefaces.connectdb.query;

/**
 *
 * @author anonymous
 */
public class Pagination {

    private int pageNumber;
    private int pageSize;

    public Pagination(int pageNumber, int pageSize) {
        if (pageNumber < 1) {
            throw new IllegalArgumentException("pageNumber must > 0");
        }

        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

}
