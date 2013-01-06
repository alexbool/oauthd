package com.alexbool.oauth;

import org.junit.Test;
import org.springframework.web.context.support.XmlWebApplicationContext;

public class ContextTest {

    public ContextTest() {
        XmlWebApplicationContext ctx = new XmlWebApplicationContext();
        ctx.setConfigLocation("classpath:context/application-context.xml");
        ctx.refresh();
    }

    @Test
    public void test() {
    }
}
