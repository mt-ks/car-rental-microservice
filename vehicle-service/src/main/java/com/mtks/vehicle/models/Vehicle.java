package com.mtks.vehicle.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mtks.vehicle.models.enums.VehicleFuelTypes;
import com.mtks.vehicle.models.enums.VehicleSegments;
import com.mtks.vehicle.models.enums.VehicleTransmissionTypes;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Setter
@Getter
@Table(name = "vehicles")
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Vehicle ID: Plate number, serial no, etc...
     */
    @Column(name = "identity",unique = true,nullable = false)
    private String identity;

    /**
     * Vehicle name: My Super Mercedes, Automatic BMW, Optional name of Toyota, ...
     */
    @Column(name = "name")
    private String name;

    /**
     * Vehicle category ID
     * @see VehicleCategory
     */
    @Column(name = "category_id")
    @JsonProperty("category_id")
    private Integer categoryId;


    /**
     * Vehicle Brand example: Toyota Corolla, Mercedes Benz, Renault Clio
     */
    @Column(name = "vehicle_brand")
    @JsonProperty("vehicle_brand")
    private String vehicleBrand;

    /**
     * Vehicle Segment: A, B, C, .. ,F, SUV etc...
     * @see VehicleSegments
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "vehicle_segment")
    @JsonProperty("vehicle_segment")
    private VehicleSegments vehicleSegment;

    /**
     * Vehicle transmission type: AUTOMATIC, MANUAL ...
     * @see VehicleTransmissionTypes
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "transmission_type")
    @JsonProperty("transmission_type")
    private VehicleTransmissionTypes transmissionType;


    /**
     * Vehicle fuel type: Gasoline, Diesel, ...
     * @see VehicleFuelTypes
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "fuel_type")
    @JsonProperty("fuel_type")
    private VehicleFuelTypes fuelType;

    /**
     * Vehicle year: 2021, 2022, 2024 ...
     */
    @Column(name = "vehicle_year")
    @JsonProperty("vehicle_year")
    private Integer vehicleYear;

    /**
     * Vehicle person capacity: 4, 8, 12 ...
     */
    @Column(name = "person_capacity")
    @JsonProperty("person_capacity")
    private Integer personCapacity;

    /**
     * Vehicle image url : https://...
     */
    @Column(name = "image")
    @JsonProperty("image")
    private String image;


    @Column(name = "office_id")
    @JsonProperty("office_id")
    private Integer officeId;

    /**
     * Daily price: 45.00 per day,
     */
    @Column(name = "price_daily")
    @JsonProperty("price_daily")
    private double priceDaily;

    /**
     * Driver license required or not;
     * true : car, motorcycle, ...
     * false: electric scooter
     */
    @Column(name = "driver_license_required")
    @JsonProperty("driver_license_required")
    private boolean driverLicenseRequired;

    /**
     * Min driver age:
     *        example for A segment -> 18
     *        example for D segment -> 24
     *        example for electric scooter -> 16
     */
    @Column(name = "min_driver_age")
    @JsonProperty("min_driver_age")
    private Integer minDriverAge;

    /**
     * Driver license min required age: 0, 1, 3 ,5 years...
     */
    @Column(name = "min_driver_license_age")
    @JsonProperty("min_driver_license_age")
    private Integer minDriverLicenseAge;

    /**
     * Security deposit example: 50.00
     */
    @Column(name = "security_deposit")
    @JsonProperty("security_deposit")
    private double securityDeposit;

    /**
     * Daily km for a vehicle: 300, 500, ...
     */
    @Column(name = "daily_km")
    @JsonProperty("daily_km")
    private Integer dailyKm;

    /**
     * Maximum mileage that can be done regardless of day
     * example
     *  max km      = 15.000
     *  daily km    = 5000
     *  rented for  = 5 days
     *  5000 * 5    = 25.000 instead "max_km"
     */
    @Column(name = "max_km")
    @JsonProperty("max_km")
    private Integer maxKm;

    /**
     * Monthly mileage limitation for rentals longer than 1 month.
     */
    @Column(name = "max_km_monthly")
    @JsonProperty("max_km_monthly")
    private Integer maxKmMonthly;

    /**
     * Location of the vehicle, latitude
     */
    @Column(name = "latitude")
    private double latitude;

    /**
     * Location of the vehicle, longitude
     */
    @Column(name = "longitude")
    private double longitude;

    /**
     * Delivery type, @Office, @Everywhere
     */
    @Column(name = "delivery_type")
    @JsonProperty("delivery_type")
    private Integer deliveryType;

    /**
     * is available car. true | false
     */
    @Column(name = "is_available")
    @JsonProperty("is_available")
    @Builder.Default
    private boolean isAvailable = true;

    /**
     * Vehicle owner: Company or User id;
     */
    @Column(name = "vehicle_owner")
    @JsonProperty("vehicle_owner")
    private String vehicleOwner;

    @Column(name = "updated_at")
    @JsonProperty("updated_at")
    private Date updatedAt;

    @Column(name = "created_at")
    @JsonProperty("created_at")
    private Date createdAt;

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", identity='" + identity + '\'' +
                ", name='" + name + '\'' +
                ", categoryId=" + categoryId +
                ", vehicleBrand='" + vehicleBrand + '\'' +
                ", vehicleSegment=" + vehicleSegment +
                ", transmissionType=" + transmissionType +
                ", fuelType=" + fuelType +
                ", vehicleYear=" + vehicleYear +
                ", personCapacity=" + personCapacity +
                ", image='" + image + '\'' +
                ", officeId=" + officeId +
                ", priceDaily=" + priceDaily +
                ", driverLicenseRequired=" + driverLicenseRequired +
                ", minDriverAge=" + minDriverAge +
                ", minDriverLicenseAge=" + minDriverLicenseAge +
                ", securityDeposit=" + securityDeposit +
                ", dailyKm=" + dailyKm +
                ", maxKm=" + maxKm +
                ", maxKmMonthly=" + maxKmMonthly +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", deliveryType=" + deliveryType +
                ", isAvailable=" + isAvailable +
                ", vehicleOwner=" + vehicleOwner +
                ", updatedAt=" + updatedAt +
                ", createdAt=" + createdAt +
                '}';
    }
}
