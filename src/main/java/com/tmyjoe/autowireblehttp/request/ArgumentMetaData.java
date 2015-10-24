package com.tmyjoe.autowireblehttp.request;


import com.tmyjoe.autowireblehttp.util.Asserts;
import com.tmyjoe.autowireblehttp.util.BodyUril;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;

/**
 * @author Tomoya
 */
abstract public class ArgumentMetaData {

    protected int order;

    protected ArgumentMetaData(int order) {
        this.order = order;
    }

    public int getOrder(){
        return order;
    }

    public static class Query extends ArgumentMetaData {

        private String parameterName;

        public Query(int order, String parameterName) {
            super(order);
            this.parameterName = parameterName;
        }

        @Override
        public int getOrder() {
            return order;
        }

        public NameValuePair toNameValuePair(Object arg) {
            Asserts.notNull(arg, "Argument must not be null.");
            //TODO handle other types
            if (arg instanceof String) {
                return new BasicNameValuePair(parameterName, (String) arg);
            } else if (arg instanceof Integer) {
                return new BasicNameValuePair(parameterName, String.valueOf((Integer) arg));
            }
            throw new IllegalArgumentException(arg.getClass().getName() + " has not been supported as query parameter");
        }
    }

    public static class PathParam extends ArgumentMetaData {

        private String parameterName;

        public PathParam(int order, String parameterName) {
            super(order);
            this.parameterName = "{" + parameterName + "}";
        }

        @Override
        public int getOrder() {
            return order;
        }

        public String replace(String path, Object value) {
            //TODO handle other types
            return path.replace(parameterName, (String) value);
        }
    }

    public static class Body extends ArgumentMetaData {

        public Body(int order) {
            super(order);
        }

        public String serialize(Object arg) throws IOException {
            if (arg instanceof String) {
                return (String) arg;
            } else {
                return BodyUril.toJsonBody(arg);
            }
        }
    }
}
