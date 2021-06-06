package com.cars2017.vehiclesservices.services.bean

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "dealer_vehicle")
class DealerVehicle {
    @Id
    var id: String? = null
    var dealerId: String? = null
    var code: String? = null
    var make: String? = null
    var model: String? = null
    var kw: Int? = null
    var year: Int? = null
    var color: String? = null
    var price: Int? = null
}


