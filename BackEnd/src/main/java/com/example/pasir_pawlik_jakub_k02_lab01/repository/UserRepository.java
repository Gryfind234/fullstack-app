package com.example.pasir_pawlik_jakub_k02_lab01.repository;

import com.example.pasir_pawlik_jakub_k02_lab01.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
