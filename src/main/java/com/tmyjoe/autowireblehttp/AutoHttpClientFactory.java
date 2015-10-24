package com.tmyjoe.autowireblehttp;

import com.tmyjoe.autowireblehttp.util.Asserts;

import java.lang.reflect.Proxy;

/**
 * Factory class to create an proxy bean for {@code AutoHttp} annotated interface.
 *
 * @author Tomoya
 */
public class AutoHttpClientFactory {

    private HttpClientProvider httpClientProvider;

    public AutoHttpClientFactory(HttpClientProvider httpClientProvider) {
        this.httpClientProvider = httpClientProvider;
    }

    @SuppressWarnings("unchecked")
    public <T> T createBean(Class<T> clazz) {
        Asserts.validateReturnType(clazz);
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[] {clazz},
            new ActualClient(httpClientProvider, clazz));
    }
}
