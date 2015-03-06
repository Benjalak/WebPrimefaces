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
public class DeleteService {

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                DBConfig.getUrl(),
                DBConfig.getUsername(),
                DBConfig.getPassword()
        );
    }

    public void deleteRow(Employee employee) throws SQLException, ClassNotFoundException {

        System.out.println("Employee ID " + employee.getId());

        Class.forName(DBConfig.getDriver());

        Connection connection = null;

        try {

            connection = getConnection();
            String sql = "DELETE FROM Employees WHERE employee_id=?";

            PreparedStatement prep = connection.prepareStatement(sql);
            prep.setInt(1, employee.getId());
            prep.executeUpdate();

            System.out.println("Record Delete Successfully");

        } finally {
            if (connection != null) {
                connection.close();
            }
        }

    }

}
