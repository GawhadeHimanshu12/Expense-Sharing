package com.expensesharing.backend.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class BalanceDto {
    private BigDecimal amount;
    private String oweFrom; // Name of the debtor
    private String oweTo;   // Name of the creditor
}