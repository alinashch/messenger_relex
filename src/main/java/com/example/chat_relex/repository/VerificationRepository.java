package com.example.chat_relex.repository;

import com.example.chat_relex.models.entity.Verification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface VerificationRepository extends JpaRepository<Verification, UUID> {

    Optional<Verification> findByCode(UUID code);
}