package com.expensesharing.backend.dto;

import java.math.BigDecimal;
import java.util.List;

import com.expensesharing.backend.model.SplitType;

import lombok.Data;

@Data
public class ExpenseRequestDto {
    private String description;
    private BigDecimal totalAmount;
    private Long paidById;
    private Long groupId;
    private SplitType splitType; // EQUAL, EXACT, or PERCENTAGE
    private List<ExpenseSplitDto> splits;
}