package com.example.pasir_pawlik_jakub_k02_lab01.repository;

import com.example.pasir_pawlik_jakub_k02_lab01.model.Group;
import com.example.pasir_pawlik_jakub_k02_lab01.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Long> {
    List<Group> findByMemberships_User(User user);
}
