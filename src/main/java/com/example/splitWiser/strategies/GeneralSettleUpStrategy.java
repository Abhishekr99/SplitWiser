package com.example.splitWiser.strategies;

import com.example.splitWiser.DTOs.Transaction;
import com.example.splitWiser.models.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@Component
public class GeneralSettleUpStrategy implements SettleUpStrategy {

    @Override
    public List<Transaction> settleUp(Map<User, Integer> balanceMap) {
        // Implement the logic for settling up using a general strategy
        // This could involve iterating through the balanceMap and creating transactions
        // based on the balances of each user.

        // Placeholder return statement
        return new ArrayList<>();
    }
}
