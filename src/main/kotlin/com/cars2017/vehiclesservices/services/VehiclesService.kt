package com.cars2017.vehiclesservices.services

import com.cars2017.vehiclesservices.repository.DealerVehiclesDao
import com.cars2017.vehiclesservices.services.bean.CsvVehicleRecord
import com.cars2017.vehiclesservices.services.bean.DealerVehicle
import com.cars2017.vehiclesservices.services.bean.JsonVehicleRecord
import com.cars2017.vehiclesservices.services.bean.SearchRequest
import com.opencsv.bean.CsvToBeanBuilder
import com.opencsv.exceptions.CsvException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.Reader

@Service
class VehiclesService {

    private val log: Logger = LoggerFactory.getLogger(this.javaClass)
    private val vehiclesDataMapper = VehiclesDataMapper()

    @Autowired
    private lateinit var vehiclesDao: DealerVehiclesDao

    @Autowired
    private lateinit var csvVehiclesDataReader: CsvVehiclesDataReader

    fun saveVehicleFromCSV(dealerId: String, file: MultipartFile) {

        val records = csvVehiclesDataReader.getCsvRecords(file).map { vehiclesDataMapper.asDealerVehicle(dealerId, it) };
        log.info("Vehicle records to save: {}", records)

        vehiclesDao.saveOrUpdate(records)
    }

    fun saveVehiclesFromJson(dealerId: String, vehicles: List<JsonVehicleRecord>) {

        val records = vehicles.map { vehiclesDataMapper.asDealerVehicle(dealerId, it) }
        log.info("Vehicle records to save: {}", records)
        vehiclesDao.saveOrUpdate(records)
    }

    fun searchVehicles(search: SearchRequest): List<JsonVehicleRecord>{

        val result = vehiclesDao.findVehicles(search).map { vehiclesDataMapper.asJsonVehicleListing(it) }
        log.info("Search result. size= {}", result.size)
        return result
    }


}