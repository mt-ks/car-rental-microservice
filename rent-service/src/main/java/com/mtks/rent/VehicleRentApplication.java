package com.mtks.rent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
public class VehicleRentApplication {

	public static void main(String[] args) {
		SpringApplication.run(VehicleRentApplication.class, args);
	}

}
