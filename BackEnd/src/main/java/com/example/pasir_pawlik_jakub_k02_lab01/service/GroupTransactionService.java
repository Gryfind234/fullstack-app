package com.example.pasir_pawlik_jakub_k02_lab01.service;

import com.example.pasir_pawlik_jakub_k02_lab01.dto.GroupTransactionDTO;
import com.example.pasir_pawlik_jakub_k02_lab01.model.*;
import com.example.pasir_pawlik_jakub_k02_lab01.repository.DebtRepository;
import com.example.pasir_pawlik_jakub_k02_lab01.repository.GroupRepository;
import com.example.pasir_pawlik_jakub_k02_lab01.repository.MembershipRepository;
import com.example.pasir_pawlik_jakub_k02_lab01.repository.TransactionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupTransactionService {
    private final GroupRepository groupRepository;
    private final MembershipRepository membershipRepository;
    private final DebtRepository debtRepository;
    private final TransactionRepository transactionRepository;

    public GroupTransactionService(
            GroupRepository groupRepository,
            MembershipRepository membershipRepository,
            DebtRepository debtRepository,
            TransactionRepository transactionRepository
    ) {
        this.groupRepository = groupRepository;
        this.membershipRepository = membershipRepository;
        this.debtRepository = debtRepository;
        this.transactionRepository = transactionRepository;
    }

    public void addGroupTransaction(GroupTransactionDTO dto, User currentUser) {
        Group group = groupRepository.findById(dto.getGroupId())
                .orElseThrow(() -> new EntityNotFoundException("Nie znaleziono grupy"));

        List<Membership> members = membershipRepository.findByGroupId(group.getId());
        List<Long> selectedUserIds = dto.getSelectedUserIds();

        if (selectedUserIds == null || selectedUserIds.isEmpty()) {
            throw new IllegalArgumentException("Nie wybrano żadnych użytkowników");
        }

        double amountPerUser = dto.getAmount() / selectedUserIds.size();

        for (Membership member : members) {
            User debtor = member.getUser();
            if (!debtor.getId().equals(currentUser.getId()) && selectedUserIds.contains(debtor.getId())) {
                Debt debt = new Debt();
                debt.setDebtor(debtor);
                debt.setCreditor(currentUser);
                debt.setGroup(group);
                debt.setAmount(amountPerUser);
                debt.setTitle(dto.getTitle());
                debtRepository.save(debt);
            }
        }

        // ✅ Dodanie transakcji EXPENSE użytkownikowi
        Transaction expenseTransaction = new Transaction(
                dto.getAmount(),
                TransactionType.EXPENSE,
                "Transakcja grupowa",
                "Wydano na grupę: " + group.getName(),
                currentUser
        );
        transactionRepository.save(expenseTransaction);
    }
}
