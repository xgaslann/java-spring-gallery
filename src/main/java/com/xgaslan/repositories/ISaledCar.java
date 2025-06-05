package com.xgaslan.repositories;

import com.xgaslan.data.entities.SaledCar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISaledCar extends JpaRepository<SaledCar, Long> {

    // Additional query methods can be defined here if needed
    // For example, to find a SaledCar by its model:
    // Optional<SaledCar> findByModel(String model);
}
