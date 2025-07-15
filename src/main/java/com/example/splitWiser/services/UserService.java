package com.example.splitWiser.services;


import com.example.splitWiser.models.User;
import com.example.splitWiser.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/*
    signUp take email, password --> create user & return user
        check is user exists - null check
        if not, create new user


 */
@Service
public class UserService {

    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    @Autowired
    UserRepository userRepository;

    public User signUp(String email, String password, String name){

        Optional<User> optUser = userRepository.findByEmail(email);
        if(optUser.isPresent()){
            throw new RuntimeException("User already exists !");
        }

        User user = new User();
        user.setEmail(email);
        user.setName(name);
        //user.setPassword(password); -- encrpty pass n store


        user.setPassword(bCryptPasswordEncoder.encode(password));

        userRepository.save(user);

        return user;



        //return null;
    }

    public User login(String email, String password){

        Optional<User> optUser = userRepository.findByEmail(email);
        if(optUser.isEmpty()){
            throw new RuntimeException("User does not exist ! please signUp");
        }

        User user = optUser.get();
        if(bCryptPasswordEncoder.matches(password, user.getPassword())){
            return optUser.get();
        }
        else{
            throw new RuntimeException("Invalid email/password !");
        }

    }
}
