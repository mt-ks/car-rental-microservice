package com.mtks.vehicle.services;

import com.mtks.vehicle.components.SecurityContextProvider;
import com.mtks.vehicle.dto.CreateVehicleDto;
import com.mtks.vehicle.dto.JwtParseDto;
import com.mtks.vehicle.dto.VehicleDto;
import com.mtks.vehicle.dto.response.VehicleResponseDto;
import com.mtks.vehicle.dto.response.GetVehiclesResponseDto;
import com.mtks.vehicle.exceptions.api.VehicleAlreadyExistsException;
import com.mtks.vehicle.exceptions.api.VehicleNotFoundException;
import com.mtks.vehicle.models.Vehicle;
import com.mtks.vehicle.repository.VehicleRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VehicleService {

    final VehicleRepository vehicleRepository;
    final ModelMapper modelMapper;
    private final SecurityContextProvider securityContextProvider;

    private final KafkaTemplate<String, Vehicle> kafkaTemplate;

    @Autowired
    public VehicleService(VehicleRepository vehicleRepository, ModelMapper modelMapper, SecurityContextProvider securityContextProvider, KafkaTemplate<String, Vehicle> kafkaTemplate) {
        this.vehicleRepository = vehicleRepository;
        this.modelMapper = modelMapper;
        this.securityContextProvider = securityContextProvider;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Transactional
    public VehicleResponseDto createVehicle(CreateVehicleDto createVehicleDto)  {
        Vehicle findByIdentity = this.findByIdentity(createVehicleDto.getIdentity());
        if (findByIdentity != null) {
            throw new VehicleAlreadyExistsException("Vehicle already exists");
        }

        JwtParseDto userCredentials = (JwtParseDto) securityContextProvider.getAuthentication().getCredentials();


        Vehicle vehicle = modelMapper.map(createVehicleDto, Vehicle.class);
        vehicle.setVehicleOwner(userCredentials.getId());

        Vehicle savedVehicle = this.vehicleRepository.save(vehicle);
        VehicleDto vehicleDto = modelMapper.map(savedVehicle, VehicleDto.class);


        this.kafkaTemplate.send("vehicles.created", savedVehicle.getIdentity(),savedVehicle);

        return VehicleResponseDto.builder()
                .vehicle(vehicleDto)
                .status("ok")
                .build();

    }

    public VehicleResponseDto findById(int id) {
        Optional<Vehicle> vehicle = this.vehicleRepository.findById(id);
        if(vehicle.isEmpty()){
            throw new VehicleNotFoundException("Vehicle does not exist");
        }

        VehicleDto vehicleDto = modelMapper.map(vehicle.get(), VehicleDto.class);

        return VehicleResponseDto.builder()
                .status("ok")
                .vehicle(vehicleDto)
                .build();
    }


    public Vehicle findByIdentity(String identity) {
        return this.vehicleRepository.findByIdentity(identity);
    }

    public GetVehiclesResponseDto findAll() {
        List<Vehicle> vehicles = this.vehicleRepository.findAll();
        List<VehicleDto> vehicleDto = vehicles.stream()
                .map(entity -> modelMapper.map(entity, VehicleDto.class))
                .collect(Collectors.toList());

        return GetVehiclesResponseDto.builder()
                .status("ok")
                .vehicles(vehicleDto)
                .build();
    }

}
