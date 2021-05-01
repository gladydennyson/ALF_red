package com.example.alfred.filter;

import org.apache.logging.log4j.ThreadContext;
import org.apache.logging.log4j.core.util.UuidUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@Configuration
@Component
public class FilterComponent implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        System.out.println("entered filter");
        HttpServletRequest httpServletRequest = (HttpServletRequest)servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse)servletResponse;
        System.out.println(" request id"+  UuidUtil.getTimeBasedUuid().toString());
        ThreadContext.put("RequestId", UuidUtil.getTimeBasedUuid().toString());
        System.out.println("local host"+httpServletRequest.getRemoteHost());
        System.out.println(" request url"+   httpServletRequest.getRequestURI() + (httpServletRequest.getQueryString() == null ? ""
                : ("?" + httpServletRequest.getQueryString())));
        ThreadContext.put("req.url",
                httpServletRequest.getRequestURI() + (httpServletRequest.getQueryString() == null ? ""
                        : ("?" + httpServletRequest.getQueryString())));

        System.out.println(" request head url"+  httpServletRequest.getRequestURI());
        ThreadContext.put("req.headUrl",httpServletRequest.getRequestURI());


        ThreadContext.put("req.method",httpServletRequest.getMethod());

        System.out.println(" request header"+  httpServletRequest.getHeader("Content-Type"));
        ThreadContext.put("header",httpServletRequest.getHeader("Content-Type"));

        System.out.println(" exception"+  httpServletRequest.getHeader("exception"));
        ThreadContext.put("exception",httpServletRequest.getHeader("exception"));

        System.out.println("request body " + String.valueOf(httpServletRequest.getInputStream()));
//        StringBuilder buffer = new StringBuilder();
//        BufferedReader reader = httpServletRequest.ge();
//        String line;
//        while ((line = reader.readLine()) != null) {
//            buffer.append(line);
//            buffer.append(System.lineSeparator());
//        }
//        String data = buffer.toString();
        ThreadContext.put("requestBodyData", String.valueOf(httpServletRequest.getInputStream()));
        try {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } finally {
            // clear the thread context
            ThreadContext.clearAll();
        }
    }

    @Override
    public void destroy() {

    }
}
