package com.example.pasir_pawlik_jakub_k02_lab01.controller;

import com.example.pasir_pawlik_jakub_k02_lab01.dto.TransactionDTO;
import com.example.pasir_pawlik_jakub_k02_lab01.model.Transaction;
import com.example.pasir_pawlik_jakub_k02_lab01.repository.TransactionRepository;
import com.example.pasir_pawlik_jakub_k02_lab01.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;
    private final TransactionRepository transactionRepository;

    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        return ResponseEntity.ok(transactionService.getAllTransactions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable Long id) {
        return ResponseEntity.ok(transactionService.getTransactionById(id));
    }
    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@Valid @RequestBody TransactionDTO transactionDTO) {
        Transaction savedTransaction = transactionService.createTransaction(transactionDTO);
        return ResponseEntity.created(URI.create("/api/transactions/" + savedTransaction.getId()))
                .body(savedTransaction);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Transaction> updateTransaction(
            @PathVariable Long id,
            @Valid @RequestBody TransactionDTO transactionDTO){
        Transaction updatedTransaction = transactionService.updateTransaction(id, transactionDTO);
        return ResponseEntity.ok(updatedTransaction);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Transaction> deleteTransaction(@PathVariable Long id) {
        transactionRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
