package com.example.splitWiser.DTOs;

import lombok.Data;

@Data
public class Transaction {
    Long paidByUserId;
    Long paidToUserId;
    int amount;
}
