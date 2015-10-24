package com.tmyjoe.autowireblehttp.util;

import org.apache.http.HttpResponse;

import java.lang.reflect.Method;

/**
 * An assertion helper class.
 *
 * @author Tomoya
 */
public class Asserts {

    public static void notNull(Object object, String msg) {
        if (object == null) {
            throw new IllegalArgumentException(msg);
        }
    }

    public static void validateReturnType(Class<?> clazz) {
        for (Method method : clazz.getDeclaredMethods()) {
            if (HttpResponse.class != method.getReturnType()) {
                throw new RuntimeException(
                    "Return type " + method.getReturnType().getName() + " has not been supported");
            }
        }
    }
}
