/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.primefaces.connectdb;


import com.primefaces.connectdb.util.PropertiesUtils;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author anonymous
 */
public class DBConfig {

    private static String driver;
    private static String url;
    private static String username;
    private static String password;

    static {
        try {
            reloadConfig();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static String getDriver() {
        return driver;
    }

    public static String getUrl() {
        return url;
    }

    public static String getUsername() {
        return username;
    }

    public static String getPassword() {
        return password;
    }

    public static void reloadConfig() throws IOException {
        Properties prop = PropertiesUtils.load("/db/conf.properties");
        driver = prop.getProperty("connection.driver");
        url = prop.getProperty("connection.url");
        username = prop.getProperty("connection.username");
        password = prop.getProperty("connection.password");
        
        System.out.println("*************************************************");
        System.out.println("driver = " + driver);
        System.out.println("url = " + url);
        System.out.println("username = " + username);
        System.out.println("password = " + password);
        System.out.println("*************************************************");
        System.out.println("");
    }
}
