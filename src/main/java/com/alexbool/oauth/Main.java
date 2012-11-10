package com.alexbool.oauth;

import org.eclipse.jetty.server.Server;
import org.springframework.web.context.support.XmlWebApplicationContext;

public class Main {

    public static void main(String[] args) throws Exception {
        XmlWebApplicationContext ctx = new XmlWebApplicationContext();
        ctx.setConfigLocation("classpath:application-context.xml");
        ctx.refresh();
        ctx.registerShutdownHook();
        
        Server jetty = ctx.getBean(Server.class);
        jetty.start();
        jetty.join();
    }
}
