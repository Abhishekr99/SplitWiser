package com.example.splitWiser.models;

import com.example.splitWiser.models.enums.ExpenseType;
import com.example.splitWiser.models.enums.UserExpenseType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class UserExpense extends BaseModel{
    @ManyToOne
    private User user;
    private int amount;
    @ManyToOne
    private Expense expense;
    @Enumerated(EnumType.STRING)
    private UserExpenseType userExpenseType;

}
