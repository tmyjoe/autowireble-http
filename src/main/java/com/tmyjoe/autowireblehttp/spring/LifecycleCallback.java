package com.tmyjoe.autowireblehttp.spring;

/**
 * Bean lifecyle callback interface for
 *
 * @author Tomoya Honjo
 */
public interface LifecycleCallback {
    void afterPropertiesSet();

    void destroy();
}
