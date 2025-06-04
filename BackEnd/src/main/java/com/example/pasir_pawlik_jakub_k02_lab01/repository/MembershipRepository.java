package com.example.pasir_pawlik_jakub_k02_lab01.repository;

import com.example.pasir_pawlik_jakub_k02_lab01.model.Membership;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MembershipRepository extends JpaRepository<Membership, Long> {
    List<Membership> findByGroupId(Long groupId);
}
