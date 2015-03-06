/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.primefaces.connectdb.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author anonymous
 */
public class PropertiesUtils {

    public static Properties load(String fromClassPath) throws IOException {
        Properties properties = new Properties();
        InputStream inputStream = null;
        try {
            inputStream = PropertiesUtils.class.getResourceAsStream(fromClassPath);
            properties.load(inputStream);
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }

        return properties;
    }
}
