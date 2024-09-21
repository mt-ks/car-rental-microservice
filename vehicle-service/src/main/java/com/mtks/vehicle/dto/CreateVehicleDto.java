package com.mtks.vehicle.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mtks.vehicle.models.enums.VehicleFuelTypes;
import com.mtks.vehicle.models.enums.VehicleSegments;
import com.mtks.vehicle.models.enums.VehicleTransmissionTypes;
import com.mtks.vehicle.validation.ValidEnum;
import com.mtks.vehicle.validation.ValidUrl;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import com.mtks.vehicle.models.Vehicle;

import java.util.Date;

/**
 * For about the attributes and their values lookup Vehicle Entity
 * @see Vehicle
 */
@Data
@Setter
@Getter
public class CreateVehicleDto {


    @NotBlank(message = "Please provide a valid identity.")
    @NotNull(message = "Please provide a valid identity.")
    private String identity;


    @NotBlank(message = "Please enter a valid vehicle name")
    private String name;

    @JsonProperty("category_id")
    @NotNull(message = "Please provide a valid category.")
    @Min(value = 1,message = "Please provide a valid category.")
    private Integer categoryId;

    @JsonProperty("vehicle_brand")
    @Size(min = 5, message = "Vehicle brand must be least 5 characters")
    @NotNull(message = "Please enter a valid vehicle brand.")
    @NotBlank(message = "Please enter a valid vehicle brand.")
    private String vehicleBrand;

    @JsonProperty("vehicle_segment")
    @NotNull(message = "Please enter a valid vehicle segment.")
    @ValidEnum(enumClass = VehicleSegments.class, message = "Please enter a valid vehicle segment.")
    private String vehicleSegment;

    @JsonProperty("transmission_type")
    @NotNull(message = "Please provide a valid transmission type.")
    @ValidEnum(enumClass = VehicleTransmissionTypes.class, message = "Please provide a valid transmission type")
    private String transmissionType;

    @JsonProperty("fuel_type")
    @NotNull(message = "Please enter a valid fuel type.")
    @ValidEnum(enumClass = VehicleFuelTypes.class, message = "Please enter a valid fuel type.")
    private String fuelType;

    @JsonProperty("vehicle_year")
    @NotNull(message = "Please enter a valid vehicle year.")
    @Min(value = 1885, message =  "Please enter a valid vehicle year.")
    private Integer vehicleYear;


    @JsonProperty("person_capacity")
    @NotNull(message = "Please enter a valid person capacity")
    @Min(value = 1, message =  "Please enter a valid person capacity")
    private Integer personCapacity;

    @JsonProperty("image")
    @NotNull(message = "Please enter a valid image url")
    @ValidUrl(message = "Please enter a valid image url")
    private String image;

    @JsonProperty("office_id")
    @NotNull(message = "Please provide a valid office.")
    @Min(value = 1,message = "Please provide a valid office.")
    private Integer officeId;

    @JsonProperty("price_daily")
    @NotNull(message = "Please provide a valid price.")
    @Min(value = 0,message = "Please provide a valid price.")
    private double priceDaily;

    /**
     * default value is: true
     */
    @JsonProperty("driver_license_required")
    @NotNull(message = "Please provide a correct value for driver license required")
    private boolean driverLicenseRequired = true;

    @JsonProperty("min_driver_age")
    @Min(value = 0,message = "Please enter valid min driver age")
    @NotNull(message = "Please enter valid min driver age")
    private Integer minDriverAge;

    @JsonProperty("min_driver_license_age")
    @Min(value = 0,message = "Please enter valid min driver license age")
    @NotNull(message = "Please enter valid min driver license age")
    private Integer minDriverLicenseAge;


    @JsonProperty("security_deposit")
    @NotNull(message = "Please provide a valid security deposit.")
    @Min(value = 0,message = "Please provide a valid security deposit.")
    private double securityDeposit;

    @JsonProperty("daily_km")
    @Min(value = 0,message = "Please enter valid daily km limit.")
    @NotNull(message = "Please enter valid daily km limit.")
    private Integer dailyKm;


    @JsonProperty("max_km")
    @Min(value = 0,message = "Please enter valid maximum km limit.")
    @NotNull(message = "Please enter valid maximum km limit.")
    private Integer maxKm;

    @JsonProperty("max_km_monthly")
    @Min(value = 0,message = "Please enter valid monthly maximum km limit.")
    @NotNull(message = "Please enter valid monthly maximum km limit.")
    private Integer maxKmMonthly;


    @JsonProperty("latitude")
    private double latitude;

    @JsonProperty("longitude")
    private double longitude;

    @JsonProperty("delivery_type")
    private Integer deliveryType;

    @JsonProperty("is_available")
    private boolean isAvailable = true;

    private Date updatedAt = new Date();
    private Date createdAt = new Date();

}
