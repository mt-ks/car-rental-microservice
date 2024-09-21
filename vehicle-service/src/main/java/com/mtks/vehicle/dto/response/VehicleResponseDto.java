package com.mtks.vehicle.dto.response;

import com.mtks.vehicle.dto.VehicleDto;
import lombok.*;


@Setter
@Getter
@Builder
public class VehicleResponseDto {
    @Builder.Default
    private String status = "ok";
    private VehicleDto vehicle;
}
