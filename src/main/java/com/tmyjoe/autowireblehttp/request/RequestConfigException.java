package com.tmyjoe.autowireblehttp.request;

/**
 * An exception thrown when any request configuration error happen.
 *
 * @author Tomoya
 */
public class RequestConfigException extends RuntimeException {
    private String msg;

    public RequestConfigException(String msg) {
        this.msg = msg;
    }
}
