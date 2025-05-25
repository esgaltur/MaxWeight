package cz.esgaltur.maxweight;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.modulith.Modulith;

/**
 * Main entry point for the MaxWeight Spring Boot application.
 * This program generates weight training programs based on
 * Professor Yuri Verkhoshansky's methodology.
 * <p>
 * The application is structured according to modulith principles:
 * - core: Contains the domain model classes
 * - program: Contains the services for generating training programs
 * - web: Contains the web controllers
 *
 * @author Sosnovich Dmitriy
 */
@SpringBootApplication
@Modulith
@ComponentScan(basePackages = {
        "cz.esgaltur.maxweight.core",
        "cz.esgaltur.maxweight.program",
        "cz.esgaltur.maxweight.web",
        "cz.esgaltur.maxweight.config"
})
public class MaxWeightApplication {

    /**
     * Application entry point for Spring Boot
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(MaxWeightApplication.class, args);
    }
}
