package com.alexbool.oauth;

import org.eclipse.jetty.server.Server;
import org.springframework.web.context.support.XmlWebApplicationContext;

public class Main {

    public static void main(String[] args) throws Exception {
        try (XmlWebApplicationContext ctx = new XmlWebApplicationContext()) {
            ctx.setConfigLocation("classpath:context/application-context.xml");
            ctx.refresh();
            ctx.registerShutdownHook();

            Server jetty = ctx.getBean(Server.class);
            jetty.start();
            jetty.join();
        }
    }
}
