package com.mtks.rent.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mtks.rent.services.grpc.RentOrchestrator;
import com.mtks.rent.services.grpc.VehicleService;

@RestController
@RequestMapping("/api/v1")
public class RentController {

    private VehicleService vehicleService;
    private RentOrchestrator rentOrchestrator;

    @Autowired
    public RentController(VehicleService vehicleService, RentOrchestrator rentOrchestrator) {
        this.vehicleService = vehicleService;
        this.rentOrchestrator = rentOrchestrator;
    }


    @GetMapping("/rental")
    public String home() {

        return "RentalX";
    }

    @PostMapping("/rent")
    public ResponseEntity<String> rent() {

        rentOrchestrator.createOrderSaga(1);


        return new ResponseEntity<>("ResponseBody", HttpStatus.OK);
    }



}
