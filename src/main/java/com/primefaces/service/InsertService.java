/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.primefaces.service;

import com.primefaces.connectdb.DBConfig;
import com.primefaces.model.Employee;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Aom-Cpe
 */
public class InsertService {

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                DBConfig.getUrl(),
                DBConfig.getUsername(),
                DBConfig.getPassword()
        );
    }

    public void insertRow(Employee employee) throws SQLException, ClassNotFoundException {

        System.out.println("Employee ID " + employee.getId());

        Class.forName(DBConfig.getDriver());

        Connection connection = null;

        try {

            connection = getConnection();
            String sql = "INSERT INTO Employees (employee_id,first_name,last_name,email,phone_number,hire_date,job_id,salary,manager_id,department_id)"
                    + "VALUES (?,"
                    + "?,"
                    + "?,"
                    + "?,"
                    + "?,"
                    + "SYSDATE,"
                    + "?,"
                    + "?,"
                    + "?,"
                    + "?)";

            PreparedStatement prep = connection.prepareStatement(sql);
            prep.setInt(1, employee.getId());
            prep.setString(2, employee.getFirstName());
            prep.setString(3, employee.getLastName());
            prep.setString(4, employee.getEmail());
            prep.setString(5, employee.getPhoneNumber());
            prep.setString(6, employee.getJobId());
            prep.setDouble(7, employee.getSalary());
            
            prep.setInt(8, employee.getManagerId());
            prep.setInt(9, employee.getDepartmentId());
            prep.executeUpdate();

            System.out.println("Record Insert Successfully");

        } finally {
            if (connection != null) {
                connection.close();
            }
        }

    }

}
