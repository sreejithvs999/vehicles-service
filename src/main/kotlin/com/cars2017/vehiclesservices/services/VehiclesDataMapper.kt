package com.cars2017.vehiclesservices.services

import com.cars2017.vehiclesservices.services.bean.CsvVehicleRecord
import com.cars2017.vehiclesservices.services.bean.DealerVehicle
import com.cars2017.vehiclesservices.services.bean.JsonVehicleRecord

class VehiclesDataMapper {

    private val PS_TO_KW_FACTOR = 0.735499

     fun asDealerVehicle(_dealerId: String, record: CsvVehicleRecord) = DealerVehicle().apply {
        dealerId = _dealerId
        code = record.code
        make = getMakePart(record.makeModel)
        model = getModelPart(record.makeModel)
        kw = asKW(record.powerPs)
        year = record.year
        color = record.color
        price = record.price
    }

     fun asDealerVehicle(_dealerId: String, record: JsonVehicleRecord) = DealerVehicle().apply {
        dealerId = _dealerId
        code = record.code
        make = record.make
        model = record.model
        kw = record.kW
        year = record.year
        color = record.color
        price = record.price
    }

     fun asJsonVehicleListing(vehicle: DealerVehicle) = JsonVehicleRecord().apply {
        code = vehicle.code
        make = vehicle.make
        model = vehicle.model
        kW = vehicle.kw
        year = vehicle.year
        color = vehicle.color
        price = vehicle.price
    }



    private fun getMakePart(makeModel: String?) = makeModel!!.split("/")[0]
    private fun getModelPart(makeModel: String?) = makeModel!!.split("/")[1]
    private fun asKW(powerPs: Int?) =  ( powerPs!! * PS_TO_KW_FACTOR).toInt()

}