package com.example.splitWiser.controllers;

import com.example.splitWiser.DTOs.LoginRequestDTO;
import com.example.splitWiser.DTOs.LoginResponseDTO;
import com.example.splitWiser.DTOs.SignUpRequestDTO;
import com.example.splitWiser.DTOs.SignUpResponseDTO;
import com.example.splitWiser.models.User;
import com.example.splitWiser.models.enums.ResponseStatus;
import com.example.splitWiser.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {
    @Autowired
    UserService userService;
    public SignUpResponseDTO signUp(SignUpRequestDTO signUpRequestDTO){
        SignUpResponseDTO signUpResponseDTO = new SignUpResponseDTO();
        try{
            // Destructuring the input
            String name= signUpRequestDTO.getName();
            String email = signUpRequestDTO.getEmail();
            String passworrd = signUpRequestDTO.getPassword();

            User user = userService.signUp(email, passworrd, name);

            // Format the output
            signUpResponseDTO.setUserId(user.getId());
            signUpResponseDTO.setResponseStatus(ResponseStatus.SUCCESS);
            signUpResponseDTO.setMessage("User signUp successful !");
        }
        catch (Exception e){
            signUpResponseDTO.setResponseStatus(ResponseStatus.FAILURE);
            signUpResponseDTO.setMessage(e.getMessage());
        }


        return signUpResponseDTO;

    }

    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO){

        LoginResponseDTO loginResponseDTO = new LoginResponseDTO();

        try{
            String email= loginRequestDTO.getEmail();
            String password  = loginRequestDTO.getPassword();
            User user = userService.login(email, password);

            loginResponseDTO.setUserId(user.getId());
            loginResponseDTO.setResponseStatus(ResponseStatus.SUCCESS);
            loginResponseDTO.setMessage("Login success !");
        }
        catch (Exception e){
            loginResponseDTO.setResponseStatus(ResponseStatus.FAILURE);
            loginResponseDTO.setMessage(e.getMessage());
        }

        return loginResponseDTO;

    }
}
