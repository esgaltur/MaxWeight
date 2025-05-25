package cz.esgaltur.maxweight.config;

import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for the web server.
 * Explicitly configures Undertow as the web server.
 */
@Configuration
public class WebServerConfig {

    /**
     * Customizes the Undertow web server factory.
     * 
     * @return A customizer for the Undertow web server factory
     */
    @Bean
    public WebServerFactoryCustomizer<UndertowServletWebServerFactory> undertowCustomizer() {
        return factory -> {
            // Configure Undertow as needed
            factory.setContextPath("");
            factory.setPort(8080);
        };
    }
}