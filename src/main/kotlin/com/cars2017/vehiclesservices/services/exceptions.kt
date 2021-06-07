package com.cars2017.vehiclesservices.services

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus


@ResponseStatus(HttpStatus.BAD_REQUEST)
class VehiclesRequestDataError(message: String?) : RuntimeException(message)

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
class VehiclesServiceError(message: String?) : RuntimeException(message)