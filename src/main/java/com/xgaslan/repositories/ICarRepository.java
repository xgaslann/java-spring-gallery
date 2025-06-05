package com.xgaslan.repositories;

import com.xgaslan.data.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICarRepository extends JpaRepository<Car, Long> {
    // Define custom query methods if needed
    // For example, findByMake, findByModel, etc.
}
