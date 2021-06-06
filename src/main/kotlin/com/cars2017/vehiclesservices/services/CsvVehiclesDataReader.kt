package com.cars2017.vehiclesservices.services

import com.cars2017.vehiclesservices.services.bean.CsvVehicleRecord
import com.opencsv.bean.CsvToBeanBuilder
import com.opencsv.exceptions.CsvException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.Reader


@Component
class CsvVehiclesDataReader {

    private val log: Logger = LoggerFactory.getLogger(this.javaClass)

    private final val CSV_SEPERATOR = ','

    fun getCsvRecords(file: MultipartFile): List<CsvVehicleRecord> {

        val reader = getReader(file)
        val records = parseRecords(reader)

        closeReader(reader)
        return records;
    }

    private fun getReader(file: MultipartFile): Reader {
        try {
            return  BufferedReader(InputStreamReader(file.inputStream))
        } catch (ioe: Exception) {
            throw VehiclesServiceError("Failed to read CSV File")
        }
    }

    private fun parseRecords(reader: Reader): List<CsvVehicleRecord> {
        try {
            return CsvToBeanBuilder<CsvVehicleRecord>(reader)
                    .withSeparator(CSV_SEPERATOR)
                    .withType(CsvVehicleRecord::class.java)
                    .build()
                    .parse()
        } catch (exc: Exception) {
            throw VehiclesRequestDataError(exc.message)
        }
    }

    private fun closeReader(reader: Reader) {
        try {
            reader.close()
        } catch (e: Exception) {
            log.warn("csv file close error", e);
        }
    }
}