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
public class UncheckedException extends RuntimeException {

    public UncheckedException() {
    }

    public UncheckedException(String message) {
        super(message);
    }

    public UncheckedException(String message, Throwable cause) {
        super(message, cause);
    }

    public UncheckedException(Throwable cause) {
        super(cause);
    }

    public UncheckedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
