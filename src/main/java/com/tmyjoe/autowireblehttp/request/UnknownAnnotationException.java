package com.tmyjoe.autowireblehttp.request;

/**
 * @author Tomoya
 */
public class UnknownAnnotationException extends RuntimeException {
    String msg;

    public UnknownAnnotationException(String msg) {
        super(msg);
        this.msg = msg;
    }
}
