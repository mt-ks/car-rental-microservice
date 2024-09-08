package com.mtks.vehicle.kafka;

import com.mtks.vehicle.models.Vehicle;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class VehicleListener {

    @KafkaListener(topics = "vehicles.created")
    public void consume(Vehicle vehicle){
        System.out.println("VEHICLE RECEIVED ******* : " + vehicle);
    }

}
