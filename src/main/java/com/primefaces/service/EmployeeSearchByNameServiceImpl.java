/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.primefaces.service;


import com.primefaces.connectdb.query.QueryBuilder;
import com.primefaces.connectdb.util.SqlUtils;
import com.primefaces.model.Employee;
import java.util.List;

/**
 *
 * @author anonymous
 */
public class EmployeeSearchByNameServiceImpl implements EmployeeSearchService {

    @Override
    public List<Employee> search(String keyword) {
        keyword = SqlUtils.wrapKeywordLike(keyword);
        
        return QueryBuilder.fromSQL("SELECT * FROM Employees WHERE LOWER(first_name) LIKE ? OR LOWER(last_name) LIKE ?")
                .addParam(keyword)
                .addParam(keyword)
                .executeforList(Employee.class);
    }

}
