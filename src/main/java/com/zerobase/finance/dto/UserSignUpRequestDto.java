package com.zerobase.finance.dto;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NotFound;

@Getter
@Setter
public class UserSignUpRequestDto {
    private String id;
    private String password;

    public boolean isValid(){
        return id != null && !id.isEmpty() && password != null && !password.isEmpty();
    }
}
