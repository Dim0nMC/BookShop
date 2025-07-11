package com.example.bookshop.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.FrameworkServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Properties;


public class WebAppInit // extends AbstractAnnotationConfigDispatcherServletInitializer
{


//    @Override
//    protected Class<?>[] getRootConfigClasses () {
//        return new Class<?>[]{AppConfig.class};
//    }
//
//    @Override
//    protected Class<?>[] getServletConfigClasses () {
//        return null;
//    }
//
//    @Override
//    protected String[] getServletMappings () {
//        return new String[]{"/"};
//    }
//
//    //https://www.logicbig.com/how-to/spring-mvc/spring-customizing-default-error-resolver.html
//    @Override
//    protected FrameworkServlet createDispatcherServlet (WebApplicationContext wac) {
//        DispatcherServlet ds = new DispatcherServlet(wac);
//        ds.setThrowExceptionIfNoHandlerFound(true);
//        return ds;
//    }
}