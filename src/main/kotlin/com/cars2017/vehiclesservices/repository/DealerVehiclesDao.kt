package com.cars2017.vehiclesservices.repository

import com.cars2017.vehiclesservices.repository.bean.DealerVehicle
import com.cars2017.vehiclesservices.services.bean.SearchRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Criteria.where
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.isEqualTo
import org.springframework.stereotype.Repository

@Repository
class DealerVehiclesDao {

    @Autowired
    private lateinit var mongoTemplate: MongoTemplate

    fun saveOrUpdate(records: List<DealerVehicle>) {

        records.forEach {
            val response = mongoTemplate.findAndReplace(
                    Query(where("dealerId").isEqualTo(it.dealerId)
                            .and("code").isEqualTo(it.code)),
                    it)

            if (response == null) {
                mongoTemplate.save(it)
            }
        }

        mongoTemplate.findAll(DealerVehicle::class.java)
    }

    fun findVehicles(search: SearchRequest): List<DealerVehicle> {

        val example = DealerVehicle().apply {
            make = search.make
            model = search.model
            color = search.color
            year = search.year
        }

        return mongoTemplate.find(Query(Criteria.byExample(example)), DealerVehicle::class.java)
    }
}