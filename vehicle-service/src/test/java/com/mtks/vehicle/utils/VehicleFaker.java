package com.mtks.vehicle.utils;

import com.mtks.vehicle.models.Vehicle;
import com.mtks.vehicle.models.enums.VehicleFuelTypes;
import com.mtks.vehicle.models.enums.VehicleSegments;
import com.mtks.vehicle.models.enums.VehicleTransmissionTypes;
import lombok.*;

import java.util.*;


public class VehicleFaker {

    private final List<Car> vehicles = new ArrayList<>();

    public VehicleFaker(){
        vehicles.add(new Car("Toyota Corolla",2022));
        vehicles.add(new Car("Toyota Yaris",2022));
        vehicles.add(new Car("Mercedes Benz",2024));
        vehicles.add(new Car("Tesla Cybertruck",2022));
        vehicles.add(new Car("Renault Megane",2022));
        vehicles.add(new Car("Renault Clio",2023));
        vehicles.add(new Car("Renault Scenic",2014));
        vehicles.add(new Car("Ford Fiesta",2013));
        vehicles.add(new Car("Ford Kuga",2023));
        vehicles.add(new Car("Ford Focus",2019));
        vehicles.add(new Car("Nissan Qashqai",2019));
        vehicles.add(new Car("Opel Astra",2019));
        vehicles.add(new Car("Opel Corsa",2023));
        vehicles.add(new Car("Opel Mokka",2025));
    }

    public Vehicle getRandomVehicle() {

        Random random = new Random();
        int randomIndex = random.nextInt(vehicles.size());
        Car car = vehicles.get(randomIndex);

        String randomId = UUID.randomUUID().toString().replaceAll("-", ""); // Remove dashes for a cleaner string

        return Vehicle.builder()
                .identity(randomId)
                .name(car.getName())
                .categoryId(1)
                .vehicleBrand(car.getName())
                .vehicleSegment(VehicleSegments.C)
                .transmissionType(VehicleTransmissionTypes.Automatic)
                .fuelType(VehicleFuelTypes.Gasoline)
                .vehicleYear(car.getYear())
                .personCapacity(5)
                .image("http://")
                .officeId(1)
                .priceDaily(45)
                .driverLicenseRequired(true)
                .minDriverAge(24)
                .minDriverLicenseAge(3)
                .securityDeposit(45)
                .dailyKm(1000)
                .maxKm(1000)
                .maxKmMonthly(4000)
                .latitude(0)
                .longitude(0)
                .deliveryType(0)
                .isAvailable(true)
                .vehicleOwner("0000101010101010101")
                .createdAt(new Date())
                .updatedAt(new Date())
                .build();
    }

    @Setter
    @Getter
    @AllArgsConstructor
    private class Car{
        private String name;
        private Integer year;
    }
}
