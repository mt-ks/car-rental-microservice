package com.mtks.vehicle.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Setter
@Getter
@Data
@JsonPropertyOrder({"id"})
public class VehicleDto extends CreateVehicleDto {
    private int id;

    @JsonProperty("vehicle_owner")
    private String vehicleOwner;
}
