package com.mtks.rent.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VehicleInfoResponseDto {
    private int id;
    private boolean isAvailable;
    private float price;
    private float securityDeposit;

}
