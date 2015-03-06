/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.primefaces.connectdb.query;


import com.primefaces.connectdb.DBConfig;
import com.primefaces.connectdb.exception.SQLUncheckedException;
import com.primefaces.connectdb.mapping.GenericAnnotationMapping;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javax.faces.component.UIInput.isEmpty;

/**
 *
 * @author anonymous
 */


    public class QueryBuilder {

    private final String sqlCode;
    private final List<Object> params;
    private Pagination pagination;

    private QueryBuilder(String sqlCode) {
        this.sqlCode = sqlCode;
        params = new LinkedList<>();
    }

    public static QueryBuilder fromSQL(String sqlCode) {
        return new QueryBuilder(sqlCode);
    }

    public QueryBuilder addParam(Object value) {
        params.add(value);
        return this;
    }

    public QueryBuilder withPagination(Pagination pagination) {
        this.pagination = pagination;
        return this;
    }

    private void setParameters(PreparedStatement statement, List<Object> parameters) throws SQLException {
        if (isEmpty(parameters)) {
            return;
        }

        for (int i = 0; i < parameters.size(); i++) {
            statement.setObject(i + 1, parameters.get(i));
        }
    }

    public <T> List<T> executeforList(final Class<T> clazz) {
        final List<T> results = new LinkedList<>();
        execute(new Callback() {

            @Override
            public void processing(ResultSet resultSet) throws Exception {
                results.addAll(GenericAnnotationMapping.fromResultSet(resultSet, clazz));
            }
        });

        return results;
    }

    private String wrapBySQLCount(String sqlCode) {
        return "SELECT count(*) as cnt FROM (" + sqlCode + ")";
    }

    private String wrapBySQLPagination(String sqlCode, Pagination pagination) {
        int first = (pagination.getPageNumber() - 1) * pagination.getPageSize();
        int last = pagination.getPageNumber() * pagination.getPageSize();

        return new StringBuilder()
                .append("SELECT item.* ")
                .append("FROM ")
                .append("    ( ")
                .append("        SELECT item.*, ")
                .append("               ROWNUM rnum ")
                .append("        FROM ( ")
                .append("            ").append(sqlCode)
                .append("        ) item ")
                .append("        WHERE ROWNUM <= ").append(last)
                .append("    ) item ")
                .append("WHERE rnum > ").append(first)
                .toString();
    }

    public long executeCount() {
        final Map<String, Long> map = new HashMap<>();
        execute(wrapBySQLCount(sqlCode), new Callback() {

            @Override
            public void processing(ResultSet resultSet) throws SQLException {
                if (resultSet.next()) {
                    map.put("count", resultSet.getLong("cnt"));
                }
            }
        });

        return map.get("count") == null ? 0 : map.get("count");
    }

    public <T> Page <T> executeforPage(final Class<T> clazz) {
        if (pagination == null) {
            throw new IllegalArgumentException("require pagination");
        }

        final Page<T> page = new Page<>(pagination, executeCount());
        execute(wrapBySQLPagination(sqlCode, pagination), new Callback() {

            @Override
            public void processing(ResultSet resultSet) throws Exception {
                page.setContents(GenericAnnotationMapping.fromResultSet(resultSet, clazz));
            }
        });

        return page;
    }

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                DBConfig.getUrl(),
                DBConfig.getUsername(),
                DBConfig.getPassword()
        );
    }

    private void execute(String sqlCode, Callback callback, List<Object> params) {
        try {
            Class.forName(DBConfig.getDriver());

            Connection connection = null;
            PreparedStatement statement = null;
            ResultSet resultSet = null;
            try {
                connection = getConnection();

                statement = connection.prepareStatement(sqlCode);
                setParameters(statement, params);
                resultSet = statement.executeQuery();
                callback.processing(resultSet);

            } finally {
                if (resultSet != null) {
                    resultSet.close();
                }

                if (statement != null) {
                    statement.close();
                }

                if (connection != null) {
                    connection.close();
                }
            }
        } catch (Exception ex) {
            throw new SQLUncheckedException(ex);
        }
    }

    private void execute(String sqlCode, Callback callback) {
        execute(sqlCode, callback, params);
    }

    public void execute(Callback callback) {
        execute(sqlCode, callback, params);
    }

    public static interface Callback {

        void processing(ResultSet resultSet) throws Exception;
    }

}
