package com.mtks.vehicle.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mtks.vehicle.models.Vehicle;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Builder
public class GetVehiclesResponseDto {
    private String status;
    @JsonProperty("vehicles")
    private List<Vehicle> vehicles;
}
