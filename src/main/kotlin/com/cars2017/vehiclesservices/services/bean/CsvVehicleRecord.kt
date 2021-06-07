package com.cars2017.vehiclesservices.services.bean

import com.opencsv.bean.CsvBindByName

class CsvVehicleRecord {

    @CsvBindByName(column = "code")
    var code: String? = null

    @CsvBindByName(column = "make/model")
    var makeModel: String? = null

    @CsvBindByName(column = "power-in-ps")
    var powerPs: Int? = null

    @CsvBindByName(column = "year")
    var year: Int? = null

    @CsvBindByName(column = "color")
    var color: String? = null

    @CsvBindByName(column = "price")
    var price: Int? = null
}