package com.example.pasir_pawlik_jakub_k02_lab01.controller;

import com.example.pasir_pawlik_jakub_k02_lab01.dto.BalanceDTO;
import com.example.pasir_pawlik_jakub_k02_lab01.dto.TransactionDTO;
import com.example.pasir_pawlik_jakub_k02_lab01.model.Transaction;
import com.example.pasir_pawlik_jakub_k02_lab01.model.User;
import com.example.pasir_pawlik_jakub_k02_lab01.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.data.jpa.repository.Query;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class TransactionGraphQLController {
    private final TransactionService transactionService;

    public TransactionGraphQLController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @QueryMapping
    public List<Transaction> transactions() {
        return transactionService.getAllTransactions();
    }

    @MutationMapping
    public Transaction addTransaction(@Valid @Argument TransactionDTO transactionDTO) {
        return transactionService.createTransaction(transactionDTO);
    }

    @MutationMapping
    public Transaction updateTransaction(
            @Argument Long id,
            @Valid @Argument TransactionDTO transactionDTO
    ) {
        return transactionService.updateTransaction(id, transactionDTO);
    }

    @MutationMapping
    public Boolean deleteTransaction(@Argument Long id) {
        transactionService.deleteTransaction(id);
        return true;
    }

    @QueryMapping
    public BalanceDTO userBalance() {
        User user = transactionService.getCurrentUser();
        return transactionService.getUserBalance(user);
    }
}
