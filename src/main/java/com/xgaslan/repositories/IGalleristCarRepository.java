package com.xgaslan.repositories;

import com.xgaslan.data.entities.GalleristCar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IGalleristCarRepository extends JpaRepository<GalleristCar, Long> {

    // Additional query methods can be defined here if needed
    // For example, to find a GalleristCar by its model:
    // Optional<GalleristCar> findByModel(String model);
}
