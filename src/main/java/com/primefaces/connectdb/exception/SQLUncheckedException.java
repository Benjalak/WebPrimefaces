/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.primefaces.connectdb.exception;

/**
 *
 * @author anonymous
 */
public class SQLUncheckedException extends UncheckedException {

    public SQLUncheckedException() {
    }

    public SQLUncheckedException(String message) {
        super(message);
    }

    public SQLUncheckedException(String message, Throwable cause) {
        super(message, cause);
    }

    public SQLUncheckedException(Throwable cause) {
        super(cause);
    }

    public SQLUncheckedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
