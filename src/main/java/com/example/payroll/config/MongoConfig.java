package com.example.payroll.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.beans.factory.annotation.Value;

@Configuration
public class MongoConfig {

    @Value("${spring.data.mongodb.uri}")
    private String mongoUri;

    @Value("${spring.data.mongodb.database}")
    private String primaryDatabaseName;

    @Value("${spring.data.mongodb.backup-database}")
    private String backupDatabaseName;

    @Bean
    public MongoClient mongoClient() {
        return MongoClients.create(mongoUri);
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), primaryDatabaseName);
    }

    @Bean
    public MongoTemplate backupMongoTemplate() {
        return new MongoTemplate(mongoClient(), backupDatabaseName);
    }
}
