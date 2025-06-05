package com.xgaslan.repositories;

import com.xgaslan.data.entities.Gallerist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IGalleristRepository extends JpaRepository<Gallerist, Long> {

    // Additional query methods can be defined here if needed
    // For example, to find a Gallerist by their name:
    // Optional<Gallerist> findByName(String name);
}
