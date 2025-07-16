package com.example.splitWiser.controllers;

import com.example.splitWiser.DTOs.SettleUpRequestDto;
import com.example.splitWiser.DTOs.SettleUpResponseDto;
import com.example.splitWiser.DTOs.Transaction;
import com.example.splitWiser.models.enums.ResponseStatus;
import com.example.splitWiser.services.SettleUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class SettleUpController {
    @Autowired
    SettleUpService settleUpService;

    public SettleUpResponseDto settleUp(SettleUpRequestDto request) { //only  setting up for group
        SettleUpResponseDto settleUpResponseDto = new SettleUpResponseDto();
        try {
            // Call the service to settle up
            List<Transaction> transactions = settleUpService.settleUp(request.getGroupId());
            settleUpResponseDto.setTransactions(transactions);
            settleUpResponseDto.setResponseStatus(ResponseStatus.SUCCESS);
            settleUpResponseDto.setMessage("Simplified settle-up Transactions generated !");
        } catch (Exception e) {
            // Handle any exceptions that occur during the settlement process
            settleUpResponseDto.setResponseStatus(ResponseStatus.FAILURE);
            settleUpResponseDto.setMessage(e.getMessage());
        }


        return settleUpResponseDto;
    }
}
