package com.greenpas.support.jetty;

import java.util.EnumSet;
import javax.servlet.DispatcherType;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

public class JettyFactory implements ApplicationContextAware {

    private WebApplicationContext applicationContext;
    
    public Server jetty() {
        Server server = new Server(8081);
        ServletContextHandler ctx = new ServletContextHandler(null, "/");

        XmlWebApplicationContext oauth2Context = new XmlWebApplicationContext();
        oauth2Context.setParent(applicationContext);
        oauth2Context.setConfigLocation("classpath:oauth2-servlet.xml");
        ServletHolder oauth2Servlet = new ServletHolder("oauth2", new DispatcherServlet(oauth2Context));
        ctx.addServlet(oauth2Servlet, "/*");
        
        XmlWebApplicationContext userContext = new XmlWebApplicationContext();
        userContext.setParent(applicationContext);
        userContext.setConfigLocation("classpath:user-servlet.xml");
        ServletHolder userServlet = new ServletHolder("user", new DispatcherServlet(userContext));
        ctx.addServlet(userServlet, "/user/*");

        ctx.addFilter(new FilterHolder(new DelegatingFilterProxy("springSecurityFilterChain", applicationContext)), "/*",
                EnumSet.of(DispatcherType.REQUEST));

        server.setHandler(ctx);
        return server;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = (WebApplicationContext) applicationContext;
    }
}
