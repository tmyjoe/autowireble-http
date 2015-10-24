package com.tmyjoe.autowireblehttp.request;

import com.google.common.annotations.VisibleForTesting;
import com.tmyjoe.autowireblehttp.http.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * Annotation processor for methods of {@AutoHttp} annotated interfaces.
 * This class process a method and create {@code RequestFactory} for the method.
 *
 * @author Tomoya
 */
public class MethodAnnotationProcessor {

    public static RequestFactory create(Method method) {
        return new RequestFactory(configure(method));
    }

    @VisibleForTesting
    static RequestConfig configure(Method method) {
        // TODO set a default configuration
        //      default setting might be provided global level and class level.
        RequestConfig config = new RequestConfig();
        method.getParameters();
        parseArgumentsAnnotation(config, method.getParameters());
        parseMethodAnnotation(config, method.getDeclaredAnnotations());
        return config;
    }

    private static void parseArgumentsAnnotation(RequestConfig configs, Parameter[] parameters) {
        for (int i = 0; i < parameters.length; i++) {
            for (Annotation a : parameters[i].getAnnotations()) {
                if (a instanceof QueryParam) {
                    configs.addQuery(
                        new ArgumentMetaData.Query(i, ((QueryParam) a).value()));
                } else if (a instanceof PathParam) {
                    if (((PathParam) a).value() == null) {
                        configs.addPathParam(
                            new ArgumentMetaData.PathParam(i, parameters[i].getName()));
                    } else {
                        configs.addPathParam(
                            new ArgumentMetaData.PathParam(i, ((PathParam) a).value()));
                    }
                } else if (a instanceof Body) {
                    configs.setBody(new ArgumentMetaData.Body(i));
                } else {
                    throw new UnknownAnnotationException(
                        "Unknown annotation found : " +  a.getClass().getName());
                }
            }
        }
    }

    private static void parseMethodAnnotation(RequestConfig configs, Annotation[] methodAnnotations) {
        for (Annotation annotation : methodAnnotations) {
            if (annotation instanceof GET) {
                configs.setHttpMethod("GET");
                configs.setRawPath(((GET) annotation).value());
            } else if (annotation instanceof POST) {
                configs.setHttpMethod("POST");
                configs.setRawPath(((POST) annotation).value());
            } else if (annotation instanceof PUT) {
                configs.setHttpMethod("POST");
                configs.setRawPath(((PUT) annotation).value());
            } else if (annotation instanceof DELETE) {
                configs.setHttpMethod("DELETE");
                configs.setRawPath(((DELETE) annotation).value());
            } else if (annotation instanceof Scheme) {
                configs.setScheme(((Scheme) annotation).value());
            } else if (annotation instanceof Host) {
                configs.setHost(((Host) annotation).value());
            } else {
                throw new UnknownAnnotationException(
                    "Unknown annotation found : " + annotation.getClass().getName());
            }
        }
    }
}
