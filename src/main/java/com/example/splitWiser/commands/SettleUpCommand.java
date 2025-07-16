package com.example.splitWiser.commands;

import com.example.splitWiser.DTOs.SettleUpRequestDto;
import com.example.splitWiser.DTOs.SettleUpResponseDto;
import com.example.splitWiser.DTOs.Transaction;
import com.example.splitWiser.controllers.SettleUpController;
import com.example.splitWiser.models.User;
import com.example.splitWiser.models.enums.ResponseStatus;
import com.example.splitWiser.repositories.GroupRepository;
import com.example.splitWiser.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class SettleUpCommand implements Command{

    @Autowired
    private SettleUpController settleUpController;
    @Autowired
    GroupRepository groupRepository;
    @Override
    public boolean matches(String input) {// "settleup <grpoupId>"
        String[] words = input.split(" ");
        return words.length == 2 && words[0].equalsIgnoreCase("settleup");
    }

    @Override
    public void execute(String input) {
        String[] words = input.split(" ");
        Long groupId = Long.parseLong(words[1]);

        SettleUpRequestDto settleUpGroupRequestDTO = new SettleUpRequestDto();
        settleUpGroupRequestDTO.setGroupId(groupId);

        SettleUpResponseDto responseDTO = settleUpController.settleUp(settleUpGroupRequestDTO);

        List<User> groupMembers = groupRepository.findMembersByGroupId(groupId);//optimise to fecth only user-id and user-name
        Map<Long, String> userMap = groupMembers.stream()
                .collect(Collectors.toMap(User::getId, user -> user.getName()));
        if(responseDTO.getResponseStatus().equals(ResponseStatus.SUCCESS)){
            for(Transaction transaction : responseDTO.getTransactions()){
                String paidByUserName = userMap.get(transaction.getPaidByUserId());
                String paidToUserName = userMap.get(transaction.getPaidToUserId());
                System.out.println(paidByUserName + " has to pay " + "₹ "+transaction.getAmount() + " to " + paidToUserName);
            }
        }else {
            System.out.println(responseDTO.getMessage());
        }
    }
}
