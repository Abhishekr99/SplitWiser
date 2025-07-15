package com.example.splitWiser.DTOs;

import com.example.splitWiser.models.enums.ResponseStatus;
import lombok.Data;

import java.util.List;
@Data
public class SettleUpResponseDto {
    ResponseStatus responseStatus;
    String message;
    List<Transaction> transactions;
}
