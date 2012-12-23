package com.alexbool.oauth;

import org.eclipse.jetty.server.Server;
import org.springframework.web.context.support.XmlWebApplicationContext;

public class Main {

    public static void main(String[] args) throws Exception {
        try (XmlWebApplicationContext ctx = new XmlWebApplicationContext()) {
            ctx.setConfigLocation("classpath:application-context.xml");
            ctx.refresh();
            ctx.registerShutdownHook();
            
            Server apiJetty = ctx.getBean("apiJetty", Server.class);
            Server adminJetty = ctx.getBean("adminJetty", Server.class);
            apiJetty.start();
            adminJetty.start();
            apiJetty.join();
            adminJetty.join();
        }
    }
}
