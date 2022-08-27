package com.epam.esm.dispatcherservlet;

import com.epam.esm.service.DataSourceConfig;
import com.epam.esm.service.WebConfig;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 * Central dispatcher for appropriate HTTP controller methods.
 */
@Component
public class DispatcherServlet extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] { WebConfig.class };
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] { DataSourceConfig.class };
    }

    /**
     * Configures given {@code servletContext}.
     * @param servletContext    to be configured for initializing in production environment
     */
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        servletContext.setInitParameter("spring.profiles.active", "prod");
        super.onStartup(servletContext);
    }
}
