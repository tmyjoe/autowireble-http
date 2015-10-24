package com.tmyjoe.autowireblehttp.request;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Request configuration for each declared method in scanned interfaces.
 * <p>
 * This class is responsible for configuration validity.
 * So if detect configuration error, throw {@code RequestConfigException}.
 * </p>
 *
 * @author Tomoya
 */
public class RequestConfig {
    // TODO Throw an exception when an illegal configuration found.
    private String scheme;
    private String host;
    private String httpMethod;
    private String rawPath;
    private Header[] headers;
    private List<ArgumentMetaData.Query> queries;

    private List<ArgumentMetaData.PathParam> pathParams;
    private ArgumentMetaData.Body body;

    public RequestConfig() {
    }

    String getScheme() {
        return scheme;
    }

    void setScheme(String baseUrl) {
        this.scheme = baseUrl;
    }

    String getHost() {
        return host;
    }

    void setHost(String host) {
        this.host = host;
    }

    Header[] getHeaders() {
        return headers;
    }

    void setHeaders(Header[] headers) {
        this.headers = headers;
    }

    String getHttpMethod() {
        return httpMethod;
    }

    void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    String getRawPath() {
        return rawPath;
    }

    void setRawPath(String rawPath) {
        this.rawPath = rawPath;
    }

    List<ArgumentMetaData.Query> getQueries() {
        if (queries == null) {
            return Collections.EMPTY_LIST;
        } else {
            return queries;
        }
    }

    void addQuery(ArgumentMetaData.Query query) {
        if (queries == null) {
            queries = new ArrayList<ArgumentMetaData.Query>();
        }
        queries.add(query);
    }

    void addPathParam(ArgumentMetaData.PathParam pathParam) {
        if (pathParams == null) {
            pathParams = new ArrayList<ArgumentMetaData.PathParam>();
        }
        pathParams.add(pathParam);
    }

    ArgumentMetaData.Body getBody() {
        return body;
    }

    void setBody(ArgumentMetaData.Body body) {
        this.body = body;
    }

    public List<ArgumentMetaData.PathParam> getPathParams() {
        if (pathParams == null) {
            return Collections.EMPTY_LIST;
        } else {
            return pathParams;
        }
    }

    public void setPathParams(List<ArgumentMetaData.PathParam> pathParams) {
        this.pathParams = pathParams;
    }

    public void setQueries(List<ArgumentMetaData.Query> queries) {
        this.queries = queries;
    }
}
