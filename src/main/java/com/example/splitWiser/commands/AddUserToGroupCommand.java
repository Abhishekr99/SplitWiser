package com.example.splitWiser.commands;

import com.example.splitWiser.models.Group;
import com.example.splitWiser.models.User;
import com.example.splitWiser.repositories.GroupRepository;
import com.example.splitWiser.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Component
public class AddUserToGroupCommand implements Command {

    @Autowired
    UserRepository userRepository;
    @Autowired
    GroupRepository groupRepository;
    @Override
    public boolean matches(String input) { // "add-users-to-group group_id user_id_1 user_id_2 ..." //avoiding username & group_name which is not unique
        return input.startsWith("add-users-to-group"); //basic check for command prefix
    }

    @Override
    public void execute(String input) {
        String[] words = input.split(" ");


        String groupId = words[1];
        Optional<Group> optGroup = groupRepository.findById(Long.parseLong(groupId));
        if(optGroup.isEmpty()) {
            System.out.println("Group with ID " + groupId + " does not exist.");
            return;
        }

        // Convert the string to a List<Long> starting from the third word
        List<Long> ids = Arrays.stream(input.split(" ")) // Split the string by spaces
                .skip(2)                 // Skip 2 words
                .map(Long::parseLong)    // Convert each element to Long
                .collect(Collectors.toList()); // Collect into a List


        List<User> members = userRepository.findAllById(ids);
        if(members.size() != ids.size()) {
            System.out.println("Some users do not exist in the database.");
            return;
        }
        Group group = optGroup.get();
        // group.setMembers(members); -- old memebers will be deleted if ur using this command to add users in ad-hoc
        // so we will add new members to existing members
        List<User> existingMembers = group.getMembers();
        if(existingMembers == null) {
            group.setMembers(members);
        }
        else{
            existingMembers.addAll(members);
            group.setMembers(existingMembers);
        }
        groupRepository.save(group);
        System.out.println("Users with IDs "+ids  +" added to group successfully.");
    }
}
