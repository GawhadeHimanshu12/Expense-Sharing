package com.expensesharing.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.expensesharing.backend.model.Balance;
import com.expensesharing.backend.model.Group;
import com.expensesharing.backend.model.User;

@Repository
public interface BalanceRepository extends JpaRepository<Balance, Long> {
    
    Optional<Balance> findByGroupAndOweFromAndOweTo(Group group, User oweFrom, User oweTo);


    List<Balance> findByGroupId(Long groupId);
}