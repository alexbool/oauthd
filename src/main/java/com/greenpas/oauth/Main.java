package com.greenpas.oauth;

import java.util.EnumSet;
import javax.servlet.DispatcherType;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

public class Main {

	public static void main(String[] args) throws Exception {
		Server server = new Server(8081);
		ServletContextHandler ctx = new ServletContextHandler(null, "/");

		ctx.setInitParameter("contextConfigLocation", "classpath:application-context.xml");
		ctx.addEventListener(new ContextLoaderListener());

		ServletHolder oauth2Servlet = new ServletHolder("oauth2", new DispatcherServlet());
		oauth2Servlet.setInitParameter("contextConfigLocation", "classpath:oauth2-servlet.xml");
		ctx.addServlet(oauth2Servlet, "/*");
		
		ServletHolder userServlet = new ServletHolder("user", new DispatcherServlet());
		userServlet.setInitParameter("contextConfigLocation", "classpath:user-servlet.xml");
		ctx.addServlet(userServlet, "/user/*");

		ctx.addFilter(new FilterHolder(new DelegatingFilterProxy("springSecurityFilterChain")), "/*",
				EnumSet.of(DispatcherType.REQUEST));

		server.setHandler(ctx);
		server.start();
		server.join();
	}
}
