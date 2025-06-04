package com.example.pasir_pawlik_jakub_k02_lab01.controller;

import com.example.pasir_pawlik_jakub_k02_lab01.dto.DebtDTO;
import com.example.pasir_pawlik_jakub_k02_lab01.model.Debt;
import com.example.pasir_pawlik_jakub_k02_lab01.model.User;
import com.example.pasir_pawlik_jakub_k02_lab01.service.DebtService;
import com.example.pasir_pawlik_jakub_k02_lab01.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class DebtGraphQLController {

    private final DebtService debtService;
    private final TransactionService transactionService;

    public DebtGraphQLController(DebtService debtService, TransactionService transactionService) {
        this.debtService = debtService;
        this.transactionService = transactionService;
    }

    @QueryMapping
    public List<Debt> groupDebts(@Argument Long groupId){
        return debtService.getGroupDebts(groupId).stream().peek(debt -> {
            if (debt.getTitle() == null) {
                debt.setTitle("Brak opisu");
            }
        })
                .toList();
    }

    @MutationMapping
    public Debt createDebt(@Valid @Argument DebtDTO debtDTO) {
        return debtService.createDebt(debtDTO);
    }

    @MutationMapping
    public Boolean deleteDebt(@Argument Long debtId) {
        User currentUser = transactionService.getCurrentUser();
        debtService.deleteDebt(debtId, currentUser);
        return true;
    }

    @MutationMapping
    public Boolean markDebtAsPaid(@Argument Long debtId) {
        User currentUser = transactionService.getCurrentUser();
        return debtService.markAsPaid(debtId, currentUser);
    }

    @MutationMapping
    public Boolean confirmDebtPayment(@Argument Long debtId) {
        User currentUser = transactionService.getCurrentUser();
        return debtService.confirmPayment(debtId, currentUser);
    }
}
