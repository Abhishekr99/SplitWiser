package com.example.splitWiser.models;

import com.example.splitWiser.models.enums.ExpenseType;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Expense extends BaseModel{
    private String description;
    private double totalAmount;
    @OneToMany(mappedBy = "expense", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<UserExpense> userExpenses;
    @ManyToOne
    private Group group;
    @Enumerated(EnumType.STRING)
    private ExpenseType expenseType;


}
