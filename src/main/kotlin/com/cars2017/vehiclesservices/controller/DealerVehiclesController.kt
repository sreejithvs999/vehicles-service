package com.cars2017.vehiclesservices.controller

import com.cars2017.vehiclesservices.services.VehiclesService
import com.cars2017.vehiclesservices.services.bean.SearchRequest
import com.cars2017.vehiclesservices.services.bean.VehicleListingRecord
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
class DealerVehiclesController {

    @Autowired
    private lateinit var vehiclesService: VehiclesService

    @PostMapping(path = ["/upload_csv/{dealerId}"])
    fun saveFromCsvFile(@RequestParam("dealerId") dealerId: String,
                        @RequestParam("file") file: MultipartFile) {
        vehiclesService.saveVehicleFromCSV(dealerId, file)
    }

    @PostMapping(path = ["/vehicle_listings/{dealerId}"])
    fun saveVehicles(@RequestParam("dealerId") dealerId: String, @RequestBody vehicleListings: List<VehicleListingRecord>) {
        vehiclesService.saveVehiclesFromJson(dealerId, vehicleListings)
    }

    @GetMapping(path = ["/search"])
    fun searchVehicles(@RequestParam searchRequest: SearchRequest): List<VehicleListingRecord> {
        return vehiclesService.searchVehicles(searchRequest)
    }
}