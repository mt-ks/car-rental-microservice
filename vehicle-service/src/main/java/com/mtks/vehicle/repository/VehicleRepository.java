package com.mtks.vehicle.repository;

import com.mtks.vehicle.models.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository  extends JpaRepository<Vehicle, Integer> {
    Vehicle findByIdentity(String identity);
}
