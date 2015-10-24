package com.tmyjoe.autowireblehttp.util;

import org.apache.http.HttpResponse;
import org.junit.Test;

/**
 * Test class for {@code Asserts}.
 *
 * @author Tomoya
 */
public class AssertsTest {

    @Test(expected = RuntimeException.class)
    public void testInvalidReturnType() {
        Asserts.validateReturnType(InterfaceWithInvalidReturnType.class);
    }

    @Test
    public void testValidReturnType() {
        Asserts.validateReturnType(InterfaceWithValidReturnType.class);
    }

    @Test
    public void testNoMethod() {
        Asserts.validateReturnType(InterfaceWithValidReturnType.class);
    }

    private interface InterfaceWithInvalidReturnType {
        HttpResponse valid();
        String invalid();
    }

    private interface InterfaceWithValidReturnType {
        HttpResponse valid();
    }

    private interface InterfaceWithNoMethod {
    }
}
