package com.expensesharing.backend.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.expensesharing.backend.dto.BalanceDto;
import com.expensesharing.backend.model.Balance;
import com.expensesharing.backend.repository.BalanceRepository;
import com.expensesharing.backend.service.BalanceService;

@RestController
@RequestMapping("/api/balances")
public class BalanceController {

    @Autowired
    private BalanceService balanceService;

    @Autowired
    private BalanceRepository balanceRepository; 

    
    @GetMapping("/{groupId}")
    public ResponseEntity<List<BalanceDto>> getGroupBalances(@PathVariable Long groupId) {
        List<Balance> balances = balanceRepository.findByGroupId(groupId);

        
        List<BalanceDto> response = balances.stream().map(balance -> {
            BalanceDto dto = new BalanceDto();
            dto.setAmount(balance.getAmount());
            dto.setOweFrom(balance.getOweFrom().getName());
            dto.setOweTo(balance.getOweTo().getName());
            return dto;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    
    @PostMapping("/settle")
    public ResponseEntity<String> settleUp(
            @RequestParam Long groupId,
            @RequestParam Long payerId,
            @RequestParam Long recipientId,
            @RequestParam BigDecimal amount) {

        balanceService.settleBalance(groupId, payerId, recipientId, amount);
        return ResponseEntity.ok("Settled Successfully");
    }
}