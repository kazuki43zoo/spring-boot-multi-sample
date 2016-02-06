package com.github.kazuki43zoo.sample.component.tracking;

import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@WebFilter(filterName = "trackingFilter")
public class TrackingFilter implements Filter {

    private static final String TRACKING_ID = "X-Track-Id";

    @Autowired
    TrackingFilterProperties properties;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        final String trackingIdKey = properties.getId();
        if (request instanceof HttpServletRequest && response instanceof HttpServletResponse) {
            String trackingId = ((HttpServletRequest) request).getHeader(trackingIdKey);
            if (trackingId == null) {
                trackingId = UUID.randomUUID().toString();
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

    @Component
    @ConfigurationProperties(prefix = "application.tracking")
    private static class TrackingFilterProperties {
        private String id = "X-Track-Id";

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

}
