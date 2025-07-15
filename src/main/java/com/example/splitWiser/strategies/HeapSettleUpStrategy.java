package com.example.splitWiser.strategies;

import com.example.splitWiser.DTOs.Transaction;
import com.example.splitWiser.models.User;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.*;
@Component
@Primary
public class HeapSettleUpStrategy implements SettleUpStrategy{
    @Override
    public List<Transaction> settleUp(Map<User, Integer> balanceMap) {

        // Home - work
        System.out.println("Heap settle up algorithm running");
        // Implement the logic for settling up using a heap-based strategy
        // This could involve creating transactions based on the balances of each user
        // and using a heap to efficiently manage the settlement process.

        PriorityQueue<Map.Entry<User, Integer>> maxHeapOfLenders = new PriorityQueue<>(
                (a, b) -> b.getValue().compareTo(a.getValue())
        );

        PriorityQueue<Map.Entry<User, Integer>> maxHeapOfBorrowers = new PriorityQueue<>(
                (a, b) -> (b.getValue().compareTo(a.getValue())*-1)
        );

        for(Map.Entry<User, Integer> entry : balanceMap.entrySet()){
            if(entry.getValue() > 0){
                maxHeapOfLenders.offer(entry);
            } else if(entry.getValue() < 0){
                maxHeapOfBorrowers.offer(entry);
            }
        }

        List<Transaction> transactions = new ArrayList<>();
        while(!maxHeapOfLenders.isEmpty() && !maxHeapOfBorrowers.isEmpty()){
            Map.Entry<User, Integer> lenderEntry = maxHeapOfLenders.poll();
            Map.Entry<User, Integer> borrowerEntry = maxHeapOfBorrowers.poll();

            User lender = lenderEntry.getKey();
            User borrower = borrowerEntry.getKey();
            int amountToSettle = Math.min(lenderEntry.getValue(), Math.abs(borrowerEntry.getValue()));

            // Create a transaction
            Transaction transaction = new Transaction();
            transaction.setPaidByUserId(borrower.getId());
            transaction.setPaidToUserId(lender.getId());
            transaction.setAmount(amountToSettle);
            transactions.add(transaction);

            // Update the balances
            if(lenderEntry.getValue() > amountToSettle){
                maxHeapOfLenders.offer(new AbstractMap.SimpleEntry<>(lender, lenderEntry.getValue() - amountToSettle));
            }
            if(-borrowerEntry.getValue() > amountToSettle){
                maxHeapOfBorrowers.offer(new AbstractMap.SimpleEntry<>(borrower, borrowerEntry.getValue() + amountToSettle));
            }
        }




        return transactions;

        // Placeholder return statement
//        return List.of();
    }
}
