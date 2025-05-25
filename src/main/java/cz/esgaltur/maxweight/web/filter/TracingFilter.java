package cz.esgaltur.maxweight.web.filter;

import brave.Tracer;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filter that logs the trace ID for each request.
 * This makes it easier to see the trace ID in the logs.
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class TracingFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(TracingFilter.class);
    private final Tracer tracer;

    @Autowired
    public TracingFilter(Tracer tracer) {
        this.tracer = tracer;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (tracer.currentSpan() != null) {
            String traceId = tracer.currentSpan().context().traceIdString();
            logger.debug("Processing request with trace ID: {}", traceId);
            response.addHeader("X-Trace-Id", traceId);
        }
        filterChain.doFilter(request, response);
    }
}