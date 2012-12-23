package com.alexbool.support.jetty;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.EnumSet;
import javax.servlet.DispatcherType;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;
import com.alexbool.util.AccessControlAllowOriginFilter;

public class JettyFactory implements ApplicationContextAware {

    private WebApplicationContext applicationContext;
    
    public Server apiJetty(String bindAddress, int bindPort) {
        Server server = new Server(new InetSocketAddress(bindAddress, bindPort));
        ServletContextHandler ctx = new ServletContextHandler(null, "/");

        XmlWebApplicationContext restContext = new XmlWebApplicationContext();
        restContext.setParent(applicationContext);
        restContext.setConfigLocation("classpath:rest-servlet.xml");
        ServletHolder restServlet = new ServletHolder("rest", new DispatcherServlet(restContext));
        ctx.addServlet(restServlet, "/*");
        
        ctx.addFilter(new FilterHolder(new DelegatingFilterProxy("springSecurityFilterChain", applicationContext)), "/*",
                EnumSet.of(DispatcherType.REQUEST));
        ctx.addFilter(new FilterHolder(new AccessControlAllowOriginFilter()), "/*", EnumSet.of(DispatcherType.REQUEST));

        server.setHandler(ctx);
        return server;
    }

    public Server adminJetty(String bindAddress, int bindPort) throws IOException {
        Server server = new Server(new InetSocketAddress(bindAddress, bindPort));
        ResourceHandler handler = new ResourceHandler();
        handler.setResourceBase(new ClassPathResource("admin").getURI().toString());
        server.setHandler(handler);
        return server;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = (WebApplicationContext) applicationContext;
    }
}
