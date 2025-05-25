package cz.esgaltur.maxweight.config;

import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;

/**
 * Configuration class for the server.
 * Explicitly configures Undertow as the web server.
 */
@Configuration
public class ServerConfig {

    private final Environment environment;
    private final ServerProperties serverProperties;

    public ServerConfig(Environment environment, ServerProperties serverProperties) {
        this.environment = environment;
        this.serverProperties = serverProperties;
    }

    /**
     * Creates and configures an Undertow servlet web server factory.
     * 
     * @return A configured UndertowServletWebServerFactory
     */
    @Bean
    @Primary
    public UndertowServletWebServerFactory undertowFactory() {
        UndertowServletWebServerFactory factory = new UndertowServletWebServerFactory();

        // Apply server properties
        factory.setPort(serverProperties.getPort() != null ? serverProperties.getPort() : 8080);
        factory.setContextPath(serverProperties.getServlet().getContextPath());

        // Additional Undertow-specific configuration can be added here

        return factory;
    }
}
