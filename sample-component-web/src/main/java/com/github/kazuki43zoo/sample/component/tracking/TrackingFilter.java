package com.github.kazuki43zoo.sample.component.tracking;

import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.IdGenerator;
import org.springframework.util.JdkIdGenerator;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "trackingFilter")
public class TrackingFilter implements Filter {

    @Autowired
    TrackingProperties properties;

    @Autowired(required = false)
    IdGenerator idGenerator = new JdkIdGenerator();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        final String trackingIdKey = properties.getIdKey();
        if (request instanceof HttpServletRequest && response instanceof HttpServletResponse) {
            String trackingId = ((HttpServletRequest) request).getHeader(trackingIdKey);
            if (trackingId == null) {
                trackingId = idGenerator.generateId().toString();
            }
            request.setAttribute(trackingIdKey, trackingId);
            ((HttpServletResponse) response).setHeader(trackingIdKey, trackingId);
            MDC.put(trackingIdKey, trackingId);
        }
        try {
            chain.doFilter(request, response);
        } finally {
            MDC.remove(trackingIdKey);
        }

    }

    @Override
    public void destroy() {
    }

}
