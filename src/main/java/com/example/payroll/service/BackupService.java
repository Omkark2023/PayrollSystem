package com.example.payroll.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class BackupService {

    private static final Logger logger = LoggerFactory.getLogger(BackupService.class);

    @Autowired
    private MongoTemplate backupMongoTemplate;

    public <T> void createBackup(T object, String collectionName) {
        try {
            backupMongoTemplate.save(object, collectionName);
            logger.info("Backup created for object in collection: {}", collectionName);
        } catch (Exception e) {
            logger.error("Failed to create backup for object in collection: {}", collectionName, e);
        }
    }
}
