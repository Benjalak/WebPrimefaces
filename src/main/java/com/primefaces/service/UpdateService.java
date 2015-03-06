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
public class UpdateService {
    
    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                DBConfig.getUrl(),
                DBConfig.getUsername(),
                DBConfig.getPassword()
        );
    }
    
    public void updateRow(Employee employee) throws SQLException, ClassNotFoundException{
     
        System.out.println("Employee ID "+employee.getId());
        
        Class.forName(DBConfig.getDriver());
        
        Connection connection = null;
        
        try{
           
            connection = getConnection();
            String sql = "UPDATE Employees "
                    + "SET first_name = ?,"
                    + "last_name = ?,"
                    + "email = ?,"
                    + "phone_number = ?,"
                    + "salary = ? "
                    + "WHERE employee_id = ?";
            
            PreparedStatement prep = connection.prepareStatement(sql);
            prep.setString(1, employee.getFirstName());
            prep.setString(2, employee.getLastName());
            prep.setString(3, employee.getEmail());
            prep.setString(4, employee.getPhoneNumber());
            prep.setDouble(5, employee.getSalary());
            prep.setInt(6, employee.getId());
            prep.executeUpdate();
            
            System.out.println("Record Update Successfully");
            
            
        }finally{
            if(connection != null){
                connection.close();
            }
        }
        
    }
    
}
