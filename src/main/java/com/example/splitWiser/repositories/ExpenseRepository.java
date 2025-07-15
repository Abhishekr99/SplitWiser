package com.example.splitWiser.repositories;

import com.example.splitWiser.models.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    // Additional query methods can be defined here if needed
}
