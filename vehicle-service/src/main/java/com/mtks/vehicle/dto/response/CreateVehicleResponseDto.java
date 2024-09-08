package com.mtks.vehicle.dto.response;

import com.mtks.vehicle.models.Vehicle;
import lombok.*;


@Setter
@Getter
@Builder
public class CreateVehicleResponseDto {
    private String status = "ok";
    private Vehicle vehicle;

}
