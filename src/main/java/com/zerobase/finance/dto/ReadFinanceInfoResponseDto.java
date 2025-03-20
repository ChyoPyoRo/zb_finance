package com.zerobase.finance.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zerobase.finance.entity.Company;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ReadFinanceInfoResponseDto {
    private CompanyDto company;
    private List<DividendDto> dividends;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DividendDto {
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        private LocalDateTime date;
        private String dividend;
    }

    public ReadFinanceInfoResponseDto(Company company, List<DividendDto> dividends) {
        this.company = new CompanyDto(company.getName(), company.getTicker());
        this.dividends = dividends;
    }
}
