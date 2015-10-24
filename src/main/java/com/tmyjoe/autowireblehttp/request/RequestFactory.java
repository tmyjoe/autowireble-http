package com.tmyjoe.autowireblehttp.request;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * Factory class for {@code HttpRequest}.
 * This class created for each declared method of {@code AutoHttp} annotated interface.
 *
 * @author Tomoya
 */
public class RequestFactory {

    private RequestConfig requestConfig;

    public RequestFactory(RequestConfig requestConfig) {
        this.requestConfig = requestConfig;
    }

    /**
     * Create {@code HttpUriRequest} for method argument.
     *
     * @param args argument passed to method.
     * @return configured request by {@code requestConfig}
     * @throws IOException
     */
    public HttpUriRequest create(Object[] args) throws IOException {
        HttpRequestBase request = null;

        if (requestConfig.getHttpMethod().equals("GET")) {
            request =  createGetRequest();
        } else if (requestConfig.getHttpMethod().equals("POST")) {
            request =  createPostRequest(args);
        } else if (requestConfig.getHttpMethod().equals("PUT")) {
            request =  createPutRequest(args);
        } else if (requestConfig.getHttpMethod().equals("DELETE")) {
            request = createDeleteRequest();
        } else {
            throw new IllegalStateException(
                "HTTP Request " + requestConfig.getHttpMethod() + " has not been supported.");
        }
        setHeaders(request);
        setUri(request, args);
        return request;
    }

    private HttpGet createGetRequest() {
        return new HttpGet();
    }

    private HttpPost createPostRequest(Object[] args) throws IOException {
        HttpPost post = new HttpPost();
        setBody(post, args);
        return post;
    }

    private HttpPut createPutRequest(Object[] args) throws IOException {
        HttpPut put = new HttpPut();
        setBody(put, args);
        return put;
    }

    private HttpDelete createDeleteRequest() {
        return new HttpDelete();
    }


    private void setHeaders(HttpRequestBase request) {
        request.setHeaders(requestConfig.getHeaders());
    }

    private void setBody(HttpEntityEnclosingRequestBase requestBase, Object[] args)
        throws IOException {
        if (requestConfig.getBody() != null) {
            HttpEntity entity = new StringEntity(
                requestConfig.getBody().serialize(args[requestConfig.getBody().getOrder()])
            );
            requestBase.setEntity(entity);
        }
    }

    private void setUri(HttpRequestBase requestBase, Object[] args) {
        requestBase.setURI(buildUri(args));
    }

    private URI buildUri(Object[] args) {
        URIBuilder builder = new URIBuilder();
        builder.setScheme(requestConfig.getScheme());
        builder.setHost(requestConfig.getHost());

        String path = requestConfig.getRawPath();
        for (ArgumentMetaData.PathParam pathParam : requestConfig.getPathParams()) {
            path = pathParam.replace(path, args[pathParam.getOrder()]);
        }

        builder.setPath(path);

        List<NameValuePair> queryParams = new ArrayList<NameValuePair>();
        for (ArgumentMetaData.Query q : requestConfig.getQueries()) {
            queryParams.add(q.toNameValuePair(args[q.getOrder()]));
        }
        builder.addParameters(queryParams);

        try {
            return builder.build();
        } catch (URISyntaxException e) {
            // URI and parameters have already validated in request config.
            // So, this should not be happen.
            throw new RuntimeException(e);
        }
    }
}
