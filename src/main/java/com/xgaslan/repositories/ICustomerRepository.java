package com.xgaslan.repositories;

import com.xgaslan.data.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ICustomerRepository extends JpaRepository<Customer, UUID> {
}
