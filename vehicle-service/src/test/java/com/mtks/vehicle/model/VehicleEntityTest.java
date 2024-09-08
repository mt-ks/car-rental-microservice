package com.mtks.vehicle.model;

import com.mtks.vehicle.models.Vehicle;
import com.mtks.vehicle.models.enums.VehicleFuelTypes;
import com.mtks.vehicle.models.enums.VehicleSegments;
import com.mtks.vehicle.models.enums.VehicleTransmissionTypes;
import com.mtks.vehicle.repository.VehicleRepository;
import com.mtks.vehicle.utils.VehicleFaker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2) // Use the real database
public class VehicleEntityTest {

    @Autowired
    private VehicleRepository vehicleRepository;

    public Vehicle createVehicleInstance(){
        return (new VehicleFaker()).getRandomVehicle();
    }

    @Test
    public void should_create_vehicle() {
        Vehicle vehicle = createVehicleInstance();
        Vehicle savedVehicle = vehicleRepository.save(vehicle);

        Assertions.assertNotNull(savedVehicle);
        Assertions.assertNotEquals(0,savedVehicle.getId());


        Assertions.assertEquals(vehicle.getIdentity(),savedVehicle.getIdentity());
        Assertions.assertEquals(vehicle.getName(),savedVehicle.getName());
        Assertions.assertEquals(vehicle.getCategoryId(),savedVehicle.getCategoryId());
        Assertions.assertEquals(vehicle.getVehicleBrand(),savedVehicle.getVehicleBrand());
        Assertions.assertEquals(vehicle.getVehicleSegment(),savedVehicle.getVehicleSegment());
        Assertions.assertEquals(vehicle.getTransmissionType(),savedVehicle.getTransmissionType());
        Assertions.assertEquals(vehicle.getFuelType(),savedVehicle.getFuelType());
        Assertions.assertEquals(vehicle.getVehicleYear(),savedVehicle.getVehicleYear());
        Assertions.assertEquals(vehicle.getPersonCapacity(),savedVehicle.getPersonCapacity());
        Assertions.assertEquals(vehicle.getImage(),savedVehicle.getImage());
        Assertions.assertEquals(vehicle.getOfficeId(),savedVehicle.getOfficeId());
        Assertions.assertEquals(vehicle.getPriceDaily(),savedVehicle.getPriceDaily());
        Assertions.assertEquals(vehicle.getCreatedAt(),savedVehicle.getCreatedAt());
        Assertions.assertEquals(vehicle.getUpdatedAt(),savedVehicle.getUpdatedAt());


    }

    @Test
    public void should_get_vehicle_by_id_and_update_vehicle() {
        Vehicle vehicleInstance = createVehicleInstance();
        Vehicle savedVehicle = vehicleRepository.save(vehicleInstance);
        Assertions.assertNotNull(savedVehicle);

        Assertions.assertEquals(1,savedVehicle.getId());

        Vehicle vehicle = vehicleRepository.getReferenceById(savedVehicle.getId());

        Assertions.assertNotNull(vehicle);


        String newVehicleName = "Toyota Hilux";
        vehicle.setName(newVehicleName);
        vehicleRepository.save(vehicle);

        Assertions.assertNotEquals(0,vehicle.getId());

        Vehicle checkSavedVehicle = vehicleRepository.getReferenceById(1);
        Assertions.assertNotNull(checkSavedVehicle);
        Assertions.assertEquals(newVehicleName,checkSavedVehicle.getName());
    }


}
