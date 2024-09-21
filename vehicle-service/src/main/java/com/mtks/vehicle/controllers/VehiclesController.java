package com.mtks.vehicle.controllers;

import com.mtks.vehicle.dto.CreateVehicleDto;
import com.mtks.vehicle.dto.response.VehicleResponseDto;
import com.mtks.vehicle.dto.response.GetVehiclesResponseDto;
import com.mtks.vehicle.services.VehicleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@Validated
public class VehiclesController {


    VehicleService vehicleService;

    @Autowired
    public VehiclesController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @Secured("vehicle:read")
    @GetMapping("/vehicles")
    public GetVehiclesResponseDto vehicles() {
        return vehicleService.findAll();
    }

    @Secured("vehicle:read")
    @GetMapping("/vehicles/{vehicleId}")
    public VehicleResponseDto vehicle(@PathVariable Integer vehicleId) {
        return vehicleService.findById(vehicleId);
    }

    @Secured("vehicle:create")
    @PostMapping("/vehicles")
    public ResponseEntity<VehicleResponseDto> create(@Valid @RequestBody CreateVehicleDto vehicle)  {
        VehicleResponseDto response = vehicleService.createVehicle(vehicle);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


}
