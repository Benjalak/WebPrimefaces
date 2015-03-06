/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.primefaces.controller;


import com.primefaces.model.Employee;
import com.primefaces.service.DeleteService;
import com.primefaces.service.EmployeeSearchService;
import com.primefaces.service.InsertService;
import com.primefaces.service.SearchServiceUtils;
import com.primefaces.service.UpdateService;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author anonymous
 */
@ManagedBean
@ViewScoped
public class EmployeeCtrl implements Serializable {

    private List<Employee> employees;
    private String query;
    private String searchBy;
    //
    private Employee employee;

    @PostConstruct
    public void postConstruct() {
        onSearch();
    }

    public void onSearch() {
        EmployeeSearchService service = SearchServiceUtils.findServiceByName(searchBy);
        employees = service.search(query);
    }
    
    public void onClear() {
        query = null;
        searchBy = null;
        onSearch();
    }

    public void onDelete() throws SQLException, ClassNotFoundException {
//        System.out.println("delelte id = " + employee.getId());
        notifyMessage();
        deleteRow(employee);
    }
    
     public void deleteRow(Employee e) throws SQLException, ClassNotFoundException {
        DeleteService service = new DeleteService();
        service.deleteRow(e);
    }


    public void notifyMessage() {
        FacesContext.getCurrentInstance()
                .addMessage(null, new FacesMessage(
                                FacesMessage.SEVERITY_INFO,
                                "Delete Employee",
                                "success (id " + employee.getId() + ")"
                        ));

    }

    public List<Employee> getEmployees() {
        if (employees == null) {
            employees = new LinkedList<>();
        }

        return employees;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getSearchBy() {
        return searchBy;
    }

    public void setSearchBy(String searchBy) {
        this.searchBy = searchBy;
    }

    private Object request(String param) {
        return FacesContext.getCurrentInstance()
                .getExternalContext()
                .getRequestParameterMap()
                .get(param);
    }

    public void onSelect() {
        Integer id = Integer.valueOf((String) request("employeeId"));
        Employee emp = new Employee();
        emp.setId(id);
        int index = getEmployees().indexOf(emp);
        employee = getEmployees().get(index);
    }

    public Employee getEmployee() {
        if (employee == null) {
            employee = new Employee();
        }

        return employee;
    }
    
    public void onUpdate() throws SQLException, ClassNotFoundException {
        updateRow(employee);
    }
    
    public void updateRow(Employee e) throws SQLException, ClassNotFoundException {
        UpdateService service = new UpdateService();
        service.updateRow(e);
    }
    public void onInsert() throws SQLException, ClassNotFoundException {
     
        employee.setId(employees.get(employees.size()-1).getId()+1);
        insertRow(employee);
    }
    public void insertRow(Employee e) throws SQLException, ClassNotFoundException {
        InsertService service = new InsertService();
        service.insertRow(e);
    }

}
