package com.zerobase.finance.controller;

import com.zerobase.finance.dto.AddCompanyRequestDto;
import com.zerobase.finance.enums.ErrorCode;
import com.zerobase.finance.service.CompanyDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/company")
@RequiredArgsConstructor
public class CompanyController{
    private final CompanyDetailService companyDetailService;

    @PostMapping("")
    public ResponseEntity<?> addNewCompany(@RequestBody AddCompanyRequestDto requestDto){
        if(!requestDto.isValid()) throw new IllegalArgumentException(ErrorCode.MISSING_OR_INVALID_PARAM.name());
        companyDetailService.saveCompanyInfo(requestDto);
        return ResponseEntity.ok().build();
    }
}
