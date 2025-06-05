package com.xgaslan.repositories;

import com.xgaslan.data.entities.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ILanguageRepository extends JpaRepository<Language, String> {
}
