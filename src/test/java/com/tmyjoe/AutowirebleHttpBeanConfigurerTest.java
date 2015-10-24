package com.tmyjoe;

import org.apache.http.HttpResponse;
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
        HttpResponse response = testBean.request1("");
        HttpResponse response2 = testBean.request2("");
    }
}
