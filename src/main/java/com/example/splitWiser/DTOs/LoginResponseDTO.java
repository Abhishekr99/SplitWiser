package com.example.splitWiser.DTOs;


import com.example.splitWiser.models.enums.ResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDTO {
    ResponseStatus responseStatus;
    Long userId;
    String message;
}
