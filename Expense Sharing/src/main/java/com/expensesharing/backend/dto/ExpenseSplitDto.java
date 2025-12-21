package com.expensesharing.backend.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ExpenseSplitDto {
    private Long userId;
    private BigDecimal amount; // Used for EXACT
    private Integer percentage; // Used for PERCENTAGE
}