package com.tmyjoe.autowireblehttp.request;

import com.tmyjoe.autowireblehttp.http.*;
import org.apache.http.HttpResponse;
import org.junit.Test;

import java.lang.reflect.Method;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Test class for {@code MethodAnnotationProcessor}.
 *
 * @author Tomoya
 */
public class MethodAnnotationProcessorTest {

    @Test
    public void testConfigure1() {
        Method method = Test1.class.getMethods()[1];
        RequestConfig requestConfig = MethodAnnotationProcessor.configure(method);

        assertThat(requestConfig.getScheme(), is("https"));
        assertThat(requestConfig.getHost(), is("www.yahoo.co.jp"));
        assertThat(requestConfig.getHttpMethod(), is("GET"));
    }

    @Test
    public void testConfigure2() {
        Method method = Test1.class.getMethods()[0];
        RequestConfig requestConfig = MethodAnnotationProcessor.configure(method);

        assertThat(requestConfig.getScheme(), is("http"));
        assertThat(requestConfig.getHost(), is("test.example.com"));
        assertThat(requestConfig.getHttpMethod(), is("POST"));
    }

    private interface Test1 {
        @GET("/")
        @Scheme("https")
        @Host("example.com")
        HttpResponse method(@QueryParam("q") String value);

        @POST("/path/{pathParam}/")
        @Scheme("http")
        @Host("test.example.com")
        HttpResponse method2(@QueryParam("query") String value);
    }
}
