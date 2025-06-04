package com.example.pasir_pawlik_jakub_k02_lab01.repository;

import com.example.pasir_pawlik_jakub_k02_lab01.model.Transaction;
import com.example.pasir_pawlik_jakub_k02_lab01.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findAllByUser(User user);
}
