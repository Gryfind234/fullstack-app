package com.example.pasir_pawlik_jakub_k02_lab01.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
public class BalanceDTO {
    @Setter
    private double totalIncome;

    @Setter
    private double totalExpense;

    @Setter
    private double balance;

    public BalanceDTO(double totalIncome, double totalExpense, double balance) {
        this.totalIncome = totalIncome;
        this.totalExpense = totalExpense;
        this.balance = balance;
    }
}
