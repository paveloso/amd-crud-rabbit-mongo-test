package com.teststation.crudrabbitmongotest.config;

import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(basePackages = {"com.teststation.crudrabbitmongotest.dao"})
public class MongoDbConfig {

}
