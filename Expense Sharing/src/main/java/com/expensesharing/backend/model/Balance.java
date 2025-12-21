package com.expensesharing.backend.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "balances")
@Data
@NoArgsConstructor
public class Balance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal amount;

    // Who owes the money?
    @ManyToOne
    @JoinColumn(name = "owe_from_id", nullable = false)
    private User oweFrom;

    // Who is getting the money?
    @ManyToOne
    @JoinColumn(name = "owe_to_id", nullable = false)
    private User oweTo;

    // Balances are isolated per group
    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;
}