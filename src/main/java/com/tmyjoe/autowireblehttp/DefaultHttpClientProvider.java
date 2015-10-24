package com.tmyjoe.autowireblehttp;

import com.tmyjoe.autowireblehttp.spring.LifecycleCallback;
import com.tmyjoe.autowireblehttp.util.Utils;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

/**
 * Default implementation of {@code HttpClientProvider}.
 * TODO : implement connection pooling and expose configuration setting.
 *
 * @author Tomoya Honjo
 */
public class DefaultHttpClientProvider implements HttpClientProvider, LifecycleCallback {

    private CloseableHttpClient httpClient;

    @Override
    public void afterPropertiesSet() {
        this.httpClient = HttpClientBuilder.create().build();
    }

    @Override
    public void destroy() {
        Utils.closeQuietly(httpClient);
    }

    @Override
    public HttpClient provide(Class<?> clazz) {
        return httpClient;
    }
}
