/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.primefaces.connectdb.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 *
 * @author anonymous
 */
public class ResultSetType {

    private final ResultSet resultSet;

    private ResultSetType(ResultSet resultSet) {
        this.resultSet = resultSet;
    }

    public static ResultSetType wrap(ResultSet resultSet) {
        return new ResultSetType(resultSet);
    }

    public <T> T getValue(String columnName, Class<T> clazz) throws SQLException {
        if (String.class.equals(clazz)) {
            return (T) resultSet.getString(columnName);
        } else if (Integer.class.equals(clazz)) {
            return (T) Integer.valueOf(resultSet.getInt(columnName));
        } else if (Long.class.equals(clazz)) {
            return (T) Long.valueOf(resultSet.getLong(columnName));
        } else if (Float.class.equals(clazz)) {
            return (T) Float.valueOf(resultSet.getFloat(columnName));
        } else if (Double.class.equals(clazz)) {
            return (T) Double.valueOf(resultSet.getDouble(columnName));
        } else if (Boolean.class.equals(clazz)) {
            return (T) Boolean.valueOf(resultSet.getBoolean(columnName));
        } else if (Date.class.equals(clazz)) {
            return (T) resultSet.getDate(columnName);
        }

        return null;
    }
}
