package com.tmyjoe.autowireblehttp.util;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

/**
 * @author Tomoya
 */
public class BodyUril {
    private static final ObjectMapper mapper = new ObjectMapper();
    public static String toJsonBody(Object object) throws IOException {
        return mapper.writeValueAsString(object);
    }
}
