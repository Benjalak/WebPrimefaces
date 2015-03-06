/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.primefaces.service;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author anonymous
 */
public class SearchServiceUtils {

    private static Map<String, EmployeeSearchService> searchServiceMap;

    private static synchronized void registerServices() {
        searchServiceMap = new HashMap<>();
        searchServiceMap.put("default", new EmployeeSearchServiceImpl());
        searchServiceMap.put("name", new EmployeeSearchByNameServiceImpl());
        searchServiceMap.put("email", new EmployeeSearchByEmailServiceImpl());
        searchServiceMap.put("phone", new EmployeeSearchByPhoneServiceImpl());
        searchServiceMap.put("salary", new EmployeeSearchBySalaryServiceImpl());
    }

    public static EmployeeSearchService findServiceByName(String name) {
        if (searchServiceMap == null) {
            registerServices();
        }

        EmployeeSearchService service = searchServiceMap.get(name);
        return service == null ? searchServiceMap.get("default") : service;
    }
}
