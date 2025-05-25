package cz.esgaltur.maxweight.config;

import brave.Tracer;
import brave.Tracing;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;


/**
 * Configuration for distributed tracing.
 * This class configures the necessary beans for tracing HTTP requests.
 * Spans are not exported to any external system, only used within the application.
 */
@Configuration
public class TracingConfig {


    @Bean
    public Tracing tracing() {
        return Tracing.newBuilder()
                .localServiceName("maxweight-service") // Name of the service for tracing
                .build();
    }

    @Bean
    public Tracer tracer(Tracing tracing) {
        return tracing.tracer();
    }
    /**
     * Creates a request logging filter that logs the request details.
     * This helps in correlating logs with trace IDs.
     *
     * @return The configured request logging filter
     */
    @Bean
    public CommonsRequestLoggingFilter requestLoggingFilter() {
        CommonsRequestLoggingFilter loggingFilter = new CommonsRequestLoggingFilter();
        loggingFilter.setIncludeClientInfo(true);
        loggingFilter.setIncludeQueryString(true);
        loggingFilter.setIncludePayload(true);
        loggingFilter.setIncludeHeaders(true);
        return loggingFilter;
    }
}
