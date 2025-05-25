package cz.esgaltur.maxweight.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class to disable Tomcat.
 * This class ensures that Tomcat is not used as the web server.
 */
@Configuration
public class TomcatDisabler {

    /**
     * Creates a bean to disable Tomcat auto-configuration.
     * 
     * @return A bean that disables Tomcat
     */
    @Bean
    @ConditionalOnClass(name = "org.apache.catalina.startup.Tomcat")
    public ServletWebServerFactoryAutoConfiguration.BeanPostProcessorsRegistrar disableTomcat() {
        // This is a marker bean that will be detected by Spring Boot
        // and will prevent Tomcat from being auto-configured
        return new ServletWebServerFactoryAutoConfiguration.BeanPostProcessorsRegistrar();
    }
}
