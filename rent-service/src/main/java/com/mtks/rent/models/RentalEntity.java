package com.mtks.rent.models;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "rentals")
public class RentalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Vehicle id, 1,2,3...
     */
    @Column(name = "vehicle_id")
    private int vehicleId;

    /**
     * User id: optional
     */
    @Column(name = "user_id")
    private String userId;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "identity_number")
    private String identityNumber;

    @Column(name = "driving_license_number")
    private String drivingLicenseNumber;

    @Column(name = "birth_date")
    private Date birthDate;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "address")
    private String address;

    @Column(name = "security_deposit_status")
    private int securityDepositStatus;

    @Column(name = "payment_status")
    private int paymentStatus;

    @Column(name = "from_location")
    private int fromLocation;

    @Column(name = "to_location")
    private int toLocation;


}
