package com.tmyjoe;

import com.tmyjoe.autowireblehttp.AutowirebleHttp;
import com.tmyjoe.autowireblehttp.http.*;
import org.apache.http.HttpResponse;

/**
 * @author Tomoya
 */
@AutowirebleHttp
public interface TestInterface {
    @GET("/")
    @Scheme("http")
    @Host("www.yahoo.co.jp")
    HttpResponse request1(@QueryParam("q") String query);

    @POST("/{path}")
    @Scheme("https")
    @Host("example.com")
    HttpResponse request2(@PathParam String path);
}
