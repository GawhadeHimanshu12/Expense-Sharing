package com.expensesharing.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.expensesharing.backend.dto.ExpenseRequestDto;
import com.expensesharing.backend.service.ExpenseService;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @PostMapping
    public ResponseEntity<String> createExpense(@RequestBody ExpenseRequestDto request) {
        expenseService.createExpense(request);
        return ResponseEntity.ok("Expense Created Successfully");
    }
}