package com.zerobase.finance.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ScrapingDataDto {
    private String companyName;
    private String companyTicker;
    private String dividend;
    private LocalDateTime date;
}
