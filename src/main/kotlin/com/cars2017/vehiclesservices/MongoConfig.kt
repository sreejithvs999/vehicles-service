package com.cars2017.vehiclesservices

import com.mongodb.client.MongoClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.data.mongodb.core.MongoClientFactoryBean
import org.springframework.data.mongodb.core.MongoTemplate


@Configuration
class MongoConfig {

    @Autowired
    lateinit var environment: Environment

    @Bean
    open fun mongo(): MongoClientFactoryBean? {

        val mongo = MongoClientFactoryBean()
        mongo.setHost(environment.getProperty("mongo.host"))
        mongo.setPort(environment.getProperty("mongo.port")!!.toInt())
        return mongo
    }

    @Bean
    fun mongoTemplate(mongoClient: MongoClient): MongoTemplate? {
        return MongoTemplate(mongoClient, "vehicles")
    }
}