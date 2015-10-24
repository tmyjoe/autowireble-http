package com.tmyjoe.autowireblehttp;


import org.apache.http.client.HttpClient;

/**
 * An interface to provide {@code HttpClient} used by {@code ActualClient}.
 *
 * @author Tomoya
 */
public interface HttpClientProvider {
    HttpClient provide(Class<?> clazz);
}
