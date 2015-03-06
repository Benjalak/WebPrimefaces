/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.primefaces.connectdb.util;

import static com.primefaces.connectdb.util.StringUtils.isEmpty;



/**
 *
 * @author anonymous
 */
public class SqlUtils {

    public static String wrapKeywordLike(String keyword) {
        if (isEmpty(keyword)) {
            return "%%";
        }

        return "%" + keyword.toLowerCase() + "%";
    }
}
