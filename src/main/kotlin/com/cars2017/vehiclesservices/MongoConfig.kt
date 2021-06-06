package com.cars2017.vehiclesservices

import com.mongodb.client.MongoClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.core.MongoClientFactoryBean
import org.springframework.data.mongodb.core.MongoTemplate


@Configuration
class MongoConfig {

    @Bean
    open fun mongo(): MongoClientFactoryBean? {

        val mongo = MongoClientFactoryBean()
        mongo.setHost("localhost")
        mongo.setPort(27017)
        return mongo
    }

    @Bean
    fun mongoTemplate(mongoClient: MongoClient): MongoTemplate? {
        return MongoTemplate(mongoClient, "test")
    }
}