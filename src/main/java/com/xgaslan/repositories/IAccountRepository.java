package com.xgaslan.repositories;

import com.xgaslan.data.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAccountRepository extends JpaRepository<Account, Long> {
    // Define custom query methods if needed
    // For example, findByUsername, findByEmail, etc.
}
