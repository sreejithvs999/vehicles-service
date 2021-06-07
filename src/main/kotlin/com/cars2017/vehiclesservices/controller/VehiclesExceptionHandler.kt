package com.cars2017.vehiclesservices.controller

import com.cars2017.vehiclesservices.services.VehiclesRequestDataError
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class VehiclesExceptionHandler {

    @ExceptionHandler(value = [Exception::class])
    fun handleException(e: Exception) =
            ResponseEntity.status(
                    when (e) {
                        is VehiclesRequestDataError -> HttpStatus.BAD_REQUEST
                        else -> HttpStatus.INTERNAL_SERVER_ERROR
                    }).body(e.message)


}