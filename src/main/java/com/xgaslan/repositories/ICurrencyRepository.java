package com.xgaslan.repositories;

import com.xgaslan.data.entities.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICurrencyRepository extends JpaRepository<Currency, String> {
}
