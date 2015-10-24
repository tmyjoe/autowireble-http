package com.tmyjoe.autowireblehttp;

import com.tmyjoe.autowireblehttp.request.MethodAnnotationProcessor;
import com.tmyjoe.autowireblehttp.request.RequestFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * An {@code Invocation handler} to provide an implementation for {@code AutoHttp} annotated interface.
 * <p>
 *   This class use {@code HttpClient} provided by {@code HttpClientProvider},
 *   its lifecycle managed by the provider, so this class just use it.
 * </p>
 */
public class ActualClient implements InvocationHandler {

    private static final Log logger = LogFactory.getLog(ActualClient.class);

    private HttpClientProvider provider;
    private Class<?> clazz;
    private Map<Method, RequestFactory> requestFactoryMap;

    public ActualClient(HttpClientProvider provider, Class<?> clazz) {
        this.provider = provider;
        this.requestFactoryMap = new HashMap<Method, RequestFactory>();
        this.clazz = clazz;
        for (Method method : clazz.getDeclaredMethods()) {
            requestFactoryMap.put(method, MethodAnnotationProcessor.create(method));
        }
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // If the method belong to Object, invoke it as it is.
        if (method.getDeclaringClass() == Object.class || !requestFactoryMap.containsKey(method)) {
            return method.invoke(this, args);
        }

        HttpUriRequest request = requestFactoryMap.get(method).create(args);
        return execute(request);
    }

    private Object execute(HttpUriRequest request) throws IOException {
        HttpClient client = provider.provide(clazz);
        assertClientNotClosed(client, request);
        logger.trace(request.toString());
        return client.execute(request);
    }

    private void assertClientNotClosed(HttpClient client, HttpUriRequest request) {
        if (client == null) {
            throw new IllegalStateException(
                "HttpClient has already destroyed. So the following http request has not executed : " +
                    request.toString()
            );
        }
    }
}
