package cz.esgaltur.maxweight;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the MaxWeight Spring Boot application.
 * This program generates weight training programs based on 
 * Professor Yuri Verkhoshansky's methodology.
 * 
 * @author Sosnovich Dmitriy
 */
@SpringBootApplication
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