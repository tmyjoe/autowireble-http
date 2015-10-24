package com.tmyjoe;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

/**
 * Test class for {@code AutoHttpBeanConfigurer}.
 * TODO: Write the actual test case by using mock web server.
 *
 * @author Tomoya
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/test/resources/applicationContext-test.xml" })
public class AutowirebleHttpBeanConfigurerTest {

    @Autowired
    private TestInterface testBean;

    @Test
    public void test() throws IOException {
        testBean.request1("");
        testBean.request2("");

        TestRequestBody body = new TestRequestBody();
        body.name = "name";
        testBean.request3(body);
    }


    public static class TestRequestBody {
        public String name;
    }
}
