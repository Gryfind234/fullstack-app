package com.example.pasir_pawlik_jakub_k02_lab01.service;

import com.example.pasir_pawlik_jakub_k02_lab01.dto.MembershipDTO;
import com.example.pasir_pawlik_jakub_k02_lab01.model.Group;
import com.example.pasir_pawlik_jakub_k02_lab01.model.Membership;
import com.example.pasir_pawlik_jakub_k02_lab01.model.User;
import com.example.pasir_pawlik_jakub_k02_lab01.repository.GroupRepository;
import com.example.pasir_pawlik_jakub_k02_lab01.repository.MembershipRepository;
import com.example.pasir_pawlik_jakub_k02_lab01.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MembershipService {
    private final MembershipRepository membershipRepository;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    public MembershipService(MembershipRepository membershipRepository, GroupRepository groupRepository, UserRepository userRepository) {
        this.membershipRepository = membershipRepository;
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
    }

    public List<Membership> getGroupMembers(Long groupId) {
        return membershipRepository.findByGroupId(groupId);
    }

    public Membership addMember(MembershipDTO membershipDTO) {
        User user = userRepository.findByEmail(membershipDTO.getUserEmail()).orElseThrow(()-> new EntityNotFoundException("Nie znaleziono użytkownika o emailu: " + membershipDTO.getUserEmail()));
        Group group = groupRepository.findById(membershipDTO.getGroupId()).orElseThrow(()-> new EntityNotFoundException("Nie znaleziono grupy o ID: " + membershipDTO.getGroupId()));

        boolean alreadyMember = membershipRepository.findByGroupId(group.getId()).stream().anyMatch(membership -> membership.getUser().getId().equals(user.getId()));

        if (alreadyMember) {
            throw new IllegalStateException("Użytkownik jest już członkiem tej grupy.");
        }

        Membership membership = new Membership();
        membership.setUser(user);
        membership.setGroup(group);
        return membershipRepository.save(membership);
    }

    public void removeMember(Long membershipId) {
        Membership membership = membershipRepository.findById(membershipId).orElseThrow(()->new EntityNotFoundException("Członkostwo nie istnieje."));

        User currentUser = getCurrentUser();
        User groupOwner = membership.getGroup().getOwner();

        if(!currentUser.getId().equals(groupOwner.getId())) {
            throw new SecurityException("Tylko właścicel grupy może usuwać członków.");
        }
        membershipRepository.deleteById(membershipId);
    }

    public User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email).orElseThrow(()->new EntityNotFoundException("Nie znaleziono użytkownika: " + email));
    }
}
