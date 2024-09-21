package com.mtks.vehicle.services;

import com.mtks.vehicle.dto.CreateVehicleDto;
import com.mtks.vehicle.dto.response.VehicleResponseDto;
import com.mtks.vehicle.models.Vehicle;
import com.mtks.vehicle.repository.VehicleRepository;
import com.mtks.vehicle.utils.VehicleFaker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.internal.InheritingConfiguration;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VehicleServiceTest {

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private VehicleService vehicleService;

    private Vehicle vehicle;

    @BeforeEach
    void setUp() {
        vehicle = (new VehicleFaker()).getRandomVehicle();

        when(modelMapper.map(any(CreateVehicleDto.class), eq(Vehicle.class)))
                .thenReturn(new Vehicle());


        when(modelMapper.getConfiguration()).thenReturn(new InheritingConfiguration());
        when(modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT)).thenReturn(new InheritingConfiguration());

        when(vehicleRepository.save(any(Vehicle.class))).thenReturn(vehicle);
        MockitoAnnotations.openMocks(this);

    }


    @Test
    public void createVehicleAndGetResponseDto(){
        CreateVehicleDto createVehicleDto = new CreateVehicleDto();

        VehicleResponseDto responseDto = vehicleService.createVehicle(createVehicleDto);

        Assertions.assertNotNull(responseDto);
        Assertions.assertNotNull(responseDto.getStatus());
        Assertions.assertEquals(responseDto.getStatus(),"ok");
        Assertions.assertNotNull(responseDto.getVehicle());
        Assertions.assertEquals(responseDto.getVehicle().getIdentity(),vehicle.getIdentity());
    }

}
