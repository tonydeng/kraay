package com.duoqu.commons;


import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Unit test for simple App.
 */
public class BaseTest {
    protected  static final Logger log = LoggerFactory.getLogger(BaseTest.class);

    @Test
    public void test(){
        log.info("test");
    }
}
