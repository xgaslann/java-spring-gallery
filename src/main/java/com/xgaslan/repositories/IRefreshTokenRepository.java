package com.xgaslan.repositories;

import com.xgaslan.data.entities.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IRefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    void deleteByToken(String token);

    List<RefreshToken> findAllByUser_Email(String email);

    void deleteByUser_Email(String email);

    void deleteAllByUser_Email(String email);
}