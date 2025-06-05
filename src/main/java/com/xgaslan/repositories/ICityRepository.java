package com.xgaslan.repositories;

import com.xgaslan.data.entities.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICityRepository extends JpaRepository<City, Long> {
    List<City> findByCountryId(Long countryId);
}
