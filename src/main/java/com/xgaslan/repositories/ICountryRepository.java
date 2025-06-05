package com.xgaslan.repositories;

import com.xgaslan.data.entities.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICountryRepository extends JpaRepository<Country, Long> {
}
