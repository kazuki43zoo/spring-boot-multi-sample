package com.github.kazuki43zoo.sample.api.component.tracking;

import org.slf4j.MDC;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@WebFilter
public class TrackingFilter implements Filter {

    private static final String TRACKING_ID = "X-Track-Id";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest && response instanceof HttpServletResponse) {
            String trackingId = ((HttpServletRequest) request).getHeader(TRACKING_ID);
            if (trackingId == null) {
                trackingId = UUID.randomUUID().toString();
            }
            request.setAttribute(TRACKING_ID, trackingId);
            ((HttpServletResponse) response).setHeader(TRACKING_ID, trackingId);
            MDC.put(TRACKING_ID, trackingId);
        }
        try {
            chain.doFilter(request, response);
        } finally {
            MDC.remove(TRACKING_ID);
        }

    }

    @Override
    public void destroy() {
    }

}
