package com.example.splitWiser.strategies;

import com.example.splitWiser.DTOs.Transaction;
import com.example.splitWiser.models.User;

import java.util.List;
import java.util.Map;

public interface SettleUpStrategy {
    List<Transaction> settleUp(Map<User, Integer> balanceMap);
}
