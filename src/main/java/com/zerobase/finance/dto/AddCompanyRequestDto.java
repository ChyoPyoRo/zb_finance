package com.zerobase.finance.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class AddCompanyRequestDto {
    private String ticker;

    public boolean isValid(){
        return ticker != null && !ticker.isEmpty();
    }
}
