package com.example.pasir_pawlik_jakub_k02_lab01.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.aspectj.bridge.IMessage;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;

@Getter
public class TransactionDTO {
    @Setter
    @NotNull(message = "Kwota nie może być pusta")
    @Min(value = 1, message="Kwota musi być większa niż 0")
    private Double amount;

    @Setter
    @NotNull(message = "Musi być Wybrana któraś wartość")
    @Pattern(regexp="INCOME|EXPENSE", message = "Powinna być wartość INCOME lub EXPENSE")
    private String type;

    @Setter
    @Size(max=50, message="Maxymalnie 50 znaków")
    private String tags;

    @Setter
    @Size(max=255, message = "Wartość nie może przekraczać 255")
    private String notes;
}
