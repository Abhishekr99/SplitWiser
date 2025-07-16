package com.example.splitWiser.services;

import com.example.splitWiser.DTOs.SettleUpRequestDto;
import com.example.splitWiser.DTOs.SettleUpResponseDto;
import com.example.splitWiser.DTOs.Transaction;
import com.example.splitWiser.models.Expense;
import com.example.splitWiser.models.Group;
import com.example.splitWiser.models.User;
import com.example.splitWiser.models.UserExpense;
import com.example.splitWiser.models.enums.UserExpenseType;
import com.example.splitWiser.repositories.GroupRepository;
import com.example.splitWiser.strategies.SettleUpStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SettleUpService {
    @Autowired
    GroupRepository groupRepository;

    @Autowired
    SettleUpStrategy settleUpStrategy;
    public List<Transaction> settleUp(Long groupId){
        /*
            * Validate the groupId
            * fetch List<Expense> from groupId
            * Generate balanceMap from List<Expense>
            * Run the settle up algorithm with balanceMap & accumulate the transactions
         */
        Optional<Group> optionalGroup = groupRepository.findById(groupId);
        if(optionalGroup.isEmpty()){
            throw new RuntimeException("Group doesn't exist");
        }
        Group group = optionalGroup.get();
        // Fetch the expenses from the group
        List<Expense> expenses = group.getExpenses();
        // Generate balanceMap from expenses
        Map<User, Double> balanceMap = new HashMap<>();
        for(Expense expense : expenses){
            for(UserExpense userExpense : expense.getUserExpenses()){
                User user = userExpense.getUser();
                balanceMap.putIfAbsent(user, 0.0);
                if(userExpense.getUserExpenseType().equals(UserExpenseType.PAID_BY)){
                    balanceMap.put(user, balanceMap.get(user) + userExpense.getAmount());
                } else if(userExpense.getUserExpenseType().equals(UserExpenseType.PAID_FOR)){
                    balanceMap.put(user, balanceMap.get(user) - userExpense.getAmount());
                }
            }
        }

        // Call the heap settle up strategy [primary bean for strategy impl] with balanceMap
        return settleUpStrategy.settleUp(balanceMap);

    }

}
