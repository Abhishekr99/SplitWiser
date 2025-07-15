package com.example.splitWiser.DTOs;


import com.example.splitWiser.models.enums.ResponseStatus;
import lombok.Data;

//@Getter
//@Setter
@Data
public class SignUpResponseDTO {

    private Long userId;
    private ResponseStatus responseStatus;
    private String message;


//    @Override
//    public String toString() {
//        return "SignUpResponseDTO{userId = " + userId +
//                " , responseStatus = " + responseStatus +
//                " , message = " + message ;
//
//    }

}

