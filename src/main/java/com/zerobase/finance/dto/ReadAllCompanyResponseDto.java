package com.zerobase.finance.dto;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class ReadAllCompanyResponseDto{
    private String companyName;
    private String companyTicker;
}
