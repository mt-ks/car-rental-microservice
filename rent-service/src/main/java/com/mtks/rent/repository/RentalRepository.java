package com.mtks.rent.repository;

import com.mtks.rent.models.RentalEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalRepository extends JpaRepository<RentalEntity, Integer> {
}
