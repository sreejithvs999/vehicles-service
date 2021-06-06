package com.cars2017.vehiclesservices

import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.testcontainers.containers.MongoDBContainer

class MongoDbInitializer : ApplicationContextInitializer<ConfigurableApplicationContext> {

    companion object {
        var mongoDBContainer = MongoDBContainer("mongo:4.4.2").apply {
            portBindings = listOf("27017:27017")
            start()
        }
    }

    override fun initialize(applicationContext: ConfigurableApplicationContext) {

        val values: TestPropertyValues = TestPropertyValues.of(
                "spring.data.mongodb.host=" + mongoDBContainer.getContainerIpAddress(),
                "spring.data.mongodb.port=27017"
        )

        values.applyTo(applicationContext)
    }

}