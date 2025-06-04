package com.example.pasir_pawlik_jakub_k02_lab01.repository;

import com.example.pasir_pawlik_jakub_k02_lab01.model.Debt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DebtRepository extends JpaRepository<Debt, Long> {
    List<Debt> findByGroupId(Long groupId);
}
