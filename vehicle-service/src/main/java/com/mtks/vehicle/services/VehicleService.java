package com.mtks.vehicle.services;

import com.mtks.vehicle.dto.CreateVehicleDto;
import com.mtks.vehicle.dto.response.CreateVehicleResponseDto;
import com.mtks.vehicle.dto.response.GetVehiclesResponseDto;
import com.mtks.vehicle.exceptions.api.VehicleAlreadyExistsException;
import com.mtks.vehicle.exceptions.api.VehicleNotFoundException;
import com.mtks.vehicle.models.Vehicle;
import com.mtks.vehicle.repository.VehicleRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VehicleService {

    final VehicleRepository vehicleRepository;
    final ModelMapper modelMapper;

    private final KafkaTemplate<String, Vehicle> kafkaTemplate;

    @Autowired
    public VehicleService(VehicleRepository vehicleRepository, ModelMapper modelMapper, KafkaTemplate<String, Vehicle> kafkaTemplate) {
        this.vehicleRepository = vehicleRepository;
        this.modelMapper = modelMapper;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Transactional
    public CreateVehicleResponseDto createVehicle(CreateVehicleDto vehicleDto)  {
        Vehicle findByIdentity = this.findByIdentity(vehicleDto.getIdentity());
        if (findByIdentity != null) {
            throw new VehicleAlreadyExistsException("Vehicle already exists");
        }

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        Vehicle vehicle = modelMapper.map(vehicleDto, Vehicle.class);
        Vehicle savedVehicle = this.vehicleRepository.save(vehicle);


        this.kafkaTemplate.send("vehicles.created", vehicle.getIdentity(),savedVehicle);

        return CreateVehicleResponseDto.builder()
                .vehicle(savedVehicle)
                .status("ok")
                .build();

    }

    public CreateVehicleResponseDto findById(int id) {
        Optional<Vehicle> vehicle = this.vehicleRepository.findById(id);
        if(vehicle.isEmpty()){
            throw new VehicleNotFoundException("Vehicle does not exist");
        }
        return CreateVehicleResponseDto.builder()
                .status("ok")
                .vehicle(vehicle.get())
                .build();
    }

    public Vehicle findByIdentity(String identity) {
        return this.vehicleRepository.findByIdentity(identity);
    }

    public GetVehiclesResponseDto findAll() {
        return GetVehiclesResponseDto.builder()
                .status("ok")
                .vehicles(this.vehicleRepository.findAll())
                .build();
    }

}
