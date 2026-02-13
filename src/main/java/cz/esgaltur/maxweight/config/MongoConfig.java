package cz.esgaltur.maxweight.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * MongoDB configuration for the application.
 */
@Configuration
@EnableMongoRepositories(basePackages = "cz.esgaltur.maxweight.repository")
@EnableMongoAuditing
public class MongoConfig {
}