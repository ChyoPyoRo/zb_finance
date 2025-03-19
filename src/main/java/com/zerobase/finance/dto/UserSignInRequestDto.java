package com.zerobase.finance.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSignInRequestDto {
    private String id;
    private String password;

    public boolean isValid(){
        return id != null && !id.isEmpty() && password != null && !password.isEmpty();
    }
}
