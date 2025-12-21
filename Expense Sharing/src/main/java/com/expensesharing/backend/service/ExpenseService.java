package com.expensesharing.backend.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.expensesharing.backend.dto.ExpenseRequestDto;
import com.expensesharing.backend.dto.ExpenseSplitDto;
import com.expensesharing.backend.model.Expense;
import com.expensesharing.backend.model.ExpenseSplit;
import com.expensesharing.backend.model.Group;
import com.expensesharing.backend.model.User;
import com.expensesharing.backend.repository.ExpenseRepository;
import com.expensesharing.backend.repository.GroupRepository;
import com.expensesharing.backend.repository.UserRepository;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private BalanceService balanceService;

    @Transactional 
    public Expense createExpense(ExpenseRequestDto request) {
        User paidBy = userRepository.findById(request.getPaidById())
                .orElseThrow(() -> new RuntimeException("Payer not found"));
        
        Group group = groupRepository.findById(request.getGroupId())
                .orElseThrow(() -> new RuntimeException("Group not found"));
        Expense expense = new Expense();
        expense.setDescription(request.getDescription());
        expense.setTotalAmount(request.getTotalAmount());
        expense.setSplitType(request.getSplitType());
        expense.setPaidBy(paidBy);
        expense.setGroup(group);
        expense.setTimestamp(LocalDateTime.now());

        switch (request.getSplitType()) {
            case EQUAL -> handleEqualSplit(expense, request);
            case EXACT -> handleExactSplit(expense, request);
            case PERCENTAGE -> handlePercentageSplit(expense, request);
        }

        return expenseRepository.save(expense);
    }

    private void handleEqualSplit(Expense expense, ExpenseRequestDto request) {
        int totalUsers = request.getSplits().size();
        BigDecimal splitAmount = request.getTotalAmount().divide(BigDecimal.valueOf(totalUsers), 2, RoundingMode.DOWN);
        
        BigDecimal totalCalculated = splitAmount.multiply(BigDecimal.valueOf(totalUsers));
        BigDecimal remainder = request.getTotalAmount().subtract(totalCalculated);

        for (int i = 0; i < totalUsers; i++) {
            ExpenseSplitDto splitDto = request.getSplits().get(i);
            BigDecimal amount = splitAmount;
            
            if (i == 0) {
                amount = amount.add(remainder);
            }
            
            addSplitToExpense(expense, splitDto.getUserId(), amount);
        }
    }

    private void handleExactSplit(Expense expense, ExpenseRequestDto request) {
        BigDecimal sum = BigDecimal.ZERO;
        
        for (ExpenseSplitDto splitDto : request.getSplits()) {
            sum = sum.add(splitDto.getAmount());
            addSplitToExpense(expense, splitDto.getUserId(), splitDto.getAmount());
        }

        // Validation
        if (sum.compareTo(request.getTotalAmount()) != 0) {
            throw new RuntimeException("Exact split amounts do not equal total amount!");
        }
    }

    private void handlePercentageSplit(Expense expense, ExpenseRequestDto request) {
        int totalPercent = 0;
        
        for (ExpenseSplitDto splitDto : request.getSplits()) {
            totalPercent += splitDto.getPercentage();
            BigDecimal amount = request.getTotalAmount()
                    .multiply(BigDecimal.valueOf(splitDto.getPercentage()))
                    .divide(BigDecimal.valueOf(100));
            
            addSplitToExpense(expense, splitDto.getUserId(), amount);
        }

        if (totalPercent != 100) {
            throw new RuntimeException("Percentages must add up to 100%!");
        }
    }

    private void addSplitToExpense(Expense expense, Long userId, BigDecimal amount) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User in split not found"));

        ExpenseSplit split = new ExpenseSplit();
        split.setExpense(expense);
        split.setUser(user);
        split.setAmountOwed(amount);

        expense.getSplits().add(split);

        balanceService.updateBalance(expense.getGroup().getId(), expense.getPaidBy().getId(), userId, amount);
    }
}