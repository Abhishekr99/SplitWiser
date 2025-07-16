package com.example.splitWiser.commands;

import com.example.splitWiser.DTOs.SignUpRequestDTO;
import com.example.splitWiser.DTOs.SignUpResponseDTO;
import com.example.splitWiser.controllers.UserController;
import com.example.splitWiser.models.enums.ResponseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RegisterUserCommand implements Command{

    @Autowired
    UserController userController;
    @Override
    public boolean matches(String input) { //"register-user name email password"
        String[] words = input.split(" ");
        return words.length == 4 && words[0].equalsIgnoreCase("register-user");
    }

    @Override
    public void execute(String input) {
        SignUpRequestDTO signUpRequestDTO = new SignUpRequestDTO();
        String[] words = input.split(" ");
        String name = words[1];
        String email = words[2];
        String password = words[3];

        signUpRequestDTO.setName( name);
        signUpRequestDTO.setEmail(email);
        signUpRequestDTO.setPassword(password);

        SignUpResponseDTO signUpResponseDTO = userController.signUp(signUpRequestDTO);
        if(signUpResponseDTO.getResponseStatus().equals(ResponseStatus.SUCCESS)){
            System.out.println("User '"+name +"' registered successfully with ID: " + signUpResponseDTO.getUserId());
        } else {
            System.out.println("Registration failed: " + signUpResponseDTO.getMessage());
        }
    }
}
