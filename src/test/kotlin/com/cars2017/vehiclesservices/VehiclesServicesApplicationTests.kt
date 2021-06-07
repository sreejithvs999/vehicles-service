package com.cars2017.vehiclesservices

import com.beust.klaxon.Klaxon
import com.cars2017.vehiclesservices.controller.DealerVehiclesController
import com.cars2017.vehiclesservices.repository.bean.DealerVehicle
import com.cars2017.vehiclesservices.services.bean.SearchRequest
import com.cars2017.vehiclesservices.services.bean.VehicleListingRecord
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.io.ClassPathResource
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.context.ContextConfiguration


@SpringBootTest
@ContextConfiguration(initializers = [MongoDbInitializer::class])
class VehiclesServicesApplicationTests {

    @Autowired
    lateinit var dealerVehiclesController: DealerVehiclesController

    @Autowired
    lateinit var mongoTemplate: MongoTemplate

    private final val DEALER_A = "Dealer-A"
    private final val DEALER_B = "Dealer-B"
    private final val VEHICLES_CSV = "vehicles.csv"

    @BeforeEach
    fun before() {

        println("Before each ... ")

        mongoTemplate.dropCollection("dealer_vehicle")
    }

    @Test
    fun `upload csv files should save the records in db`() {

        //arrange
        val file = MockMultipartFile(VEHICLES_CSV, ClassPathResource(VEHICLES_CSV).inputStream)

        //act
        dealerVehiclesController.saveFromCsvFile(DEALER_A, file);

        //assert
        val savedRecords = mongoTemplate.findAll(DealerVehicle::class.java)
        Assertions.assertThat(savedRecords).hasSize(4)
    }

    @Test
    fun `save vehicles with same input json two times for same dealer should not make duplicate records in db`() {

        //arrange
        val vehicles = Klaxon().parseArray<VehicleListingRecord>(VEHICLES_JSON_1) as List<VehicleListingRecord>

        //act
        dealerVehiclesController.saveVehicles(DEALER_B, vehicles);
        dealerVehiclesController.saveVehicles(DEALER_B, vehicles);

        //assert
        val savedRecords = mongoTemplate.findAll(DealerVehicle::class.java)
        Assertions.assertThat(savedRecords).hasSize(3)
    }

    @Test
    fun `save vehicles with same input json two times for different dealer should should save all records in db`() {

        //arrange
        val vehicles = Klaxon().parseArray<VehicleListingRecord>(VEHICLES_JSON_1) as List<VehicleListingRecord>

        //act
        dealerVehiclesController.saveVehicles(DEALER_A, vehicles);
        dealerVehiclesController.saveVehicles(DEALER_B, vehicles);

        //assert
        val savedRecords = mongoTemplate.findAll(DealerVehicle::class.java)
        Assertions.assertThat(savedRecords).hasSize(6)
    }

    @Test
    fun `search vehicles should return matching items`() {

        //arrange
        val vehicles = Klaxon().parseArray<VehicleListingRecord>(VEHICLES_JSON_2) as List<VehicleListingRecord>

        //act
        dealerVehiclesController.saveVehicles(DEALER_A, vehicles);
        dealerVehiclesController.saveVehicles(DEALER_B, vehicles);

        val searchBlackAudi = SearchRequest(make = "audi", color = "black")
        val searchBlackAudiResult = dealerVehiclesController.searchVehicles(searchBlackAudi)

        val searchBMW2016 = SearchRequest(make = "bmw", year = 2016)
        val searchBMW2016Result = dealerVehiclesController.searchVehicles(searchBMW2016)

        val searchMegane2014Red = SearchRequest(model = "megane", year = 2014, color = "red")
        val searchMegane2014RedResult = dealerVehiclesController.searchVehicles(searchMegane2014Red)

        //assert
        Assertions.assertThat(searchBlackAudiResult).map<String> { it.color }.containsOnly("black")
        Assertions.assertThat(searchBlackAudiResult).map<String> { it.make }.containsOnly("audi")

        Assertions.assertThat(searchBMW2016Result).map<Int> { it.year }.containsOnly(2016)
        Assertions.assertThat(searchBMW2016Result).map<String> { it.make }.containsOnly("bmw")

        Assertions.assertThat(searchMegane2014RedResult).map<String> { it.model }.containsOnly("megane")
        Assertions.assertThat(searchMegane2014RedResult).map<Int> { it.year }.containsOnly(2014)
        Assertions.assertThat(searchMegane2014RedResult).map<String> { it.color }.containsOnly("red")
    }

}
