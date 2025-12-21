package com.expensesharing.backend.service;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.expensesharing.backend.model.Balance;
import com.expensesharing.backend.model.Group;
import com.expensesharing.backend.model.User;
import com.expensesharing.backend.repository.BalanceRepository;
import com.expensesharing.backend.repository.GroupRepository;
import com.expensesharing.backend.repository.UserRepository;

@Service
public class BalanceService {

    @Autowired
    private BalanceRepository balanceRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    public void updateBalance(Long groupId, Long paidById, Long owedById, BigDecimal amount) {
        if (paidById.equals(owedById)) return;

        Group group = groupRepository.findById(groupId).orElseThrow();
        User paidBy = userRepository.findById(paidById).orElseThrow();
        User owedBy = userRepository.findById(owedById).orElseThrow();

        Optional<Balance> reverseBalanceOpt = balanceRepository.findByGroupAndOweFromAndOweTo(group, paidBy, owedBy);

        if (reverseBalanceOpt.isPresent()) {
            Balance reverseBalance = reverseBalanceOpt.get();
            BigDecimal existingDebt = reverseBalance.getAmount();

            if (existingDebt.compareTo(amount) > 0) {
                reverseBalance.setAmount(existingDebt.subtract(amount));
                balanceRepository.save(reverseBalance);
                return; 
            } else {
                amount = amount.subtract(existingDebt); 
                balanceRepository.delete(reverseBalance); 
                
                if (amount.compareTo(BigDecimal.ZERO) == 0) return;
            }
        }
        Optional<Balance> balanceOpt = balanceRepository.findByGroupAndOweFromAndOweTo(group, owedBy, paidBy);
        
        Balance balance;
        if (balanceOpt.isPresent()) {
            balance = balanceOpt.get();
            balance.setAmount(balance.getAmount().add(amount));
        } else {
            balance = new Balance();
            balance.setGroup(group);
            balance.setOweFrom(owedBy);
            balance.setOweTo(paidBy);
            balance.setAmount(amount);
        }
        
        balanceRepository.save(balance);
    }

    public void settleBalance(Long groupId, Long payerId, Long recipientId, BigDecimal amount) {

        Group group = groupRepository.findById(groupId).orElseThrow();
        User payer = userRepository.findById(payerId).orElseThrow();
        User recipient = userRepository.findById(recipientId).orElseThrow();

        Balance balance = balanceRepository.findByGroupAndOweFromAndOweTo(group, payer, recipient)
                .orElseThrow(() -> new RuntimeException("No balance found to settle!"));


        if (amount.compareTo(balance.getAmount()) > 0) {
             throw new RuntimeException("Settlement amount cannot exceed owed amount!");
        }

        BigDecimal newAmount = balance.getAmount().subtract(amount);
        
        if (newAmount.compareTo(BigDecimal.ZERO) == 0) {
            balanceRepository.delete(balance); // Debt cleared fully
        } else {
            balance.setAmount(newAmount); // Partial payment
            balanceRepository.save(balance);
        }
    }
}