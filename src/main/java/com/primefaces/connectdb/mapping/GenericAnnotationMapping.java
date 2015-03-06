/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.primefaces.connectdb.mapping;


import com.primefaces.connectdb.annotation.Column;
import com.primefaces.connectdb.util.ResultSetType;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author anonymous
 */
public class GenericAnnotationMapping {

    private static String firstChar2UpperCase(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    private static Method getSetterMethodOfField(Object object, Field field) throws Exception {
        String setterName = "set" + firstChar2UpperCase(field.getName());
        return object.getClass().getDeclaredMethod(setterName, field.getType());
    }

    private static String getColumnName(Field field) {
        if (field.isAnnotationPresent(Column.class)) {
            return field.getAnnotation(Column.class).name();
        } else {
            return field.getName();
        }
    }

    private static <T> T transformResultReset(ResultSet resultSet, Class<T> clazz) throws Exception {
        T instance = clazz.newInstance();
        Field[] fields = clazz.getDeclaredFields();
        ResultSetType type = ResultSetType.wrap(resultSet);
        
        for (Field field : fields) {
            String columnName = getColumnName(field);
            Method method = getSetterMethodOfField(instance, field);
            method.invoke(instance, type.getValue(columnName, field.getType()));
        }

        return instance;
    }

    public static <T> List<T> fromResultSet(ResultSet resultSet, Class<T> clazz) throws Exception {
        List<T> list = new LinkedList<>();
        while (resultSet.next()) {
            list.add(transformResultReset(resultSet, clazz));
        }

        return list;
    }
}
