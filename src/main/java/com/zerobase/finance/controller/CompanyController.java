package com.zerobase.finance.controller;

import com.zerobase.finance.dto.AddCompanyRequestDto;
import com.zerobase.finance.dto.ResponseDto;
import com.zerobase.finance.enums.ErrorCode;
import com.zerobase.finance.service.CompanyDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@Slf4j
@RequestMapping("/company")
@RequiredArgsConstructor
public class CompanyController{
    private final CompanyDetailService companyDetailService;

    @PostMapping("")
    public ResponseEntity<?> addNewCompany(@RequestBody AddCompanyRequestDto requestDto) throws IOException, IllegalAccessException {
        if(!requestDto.isValid()) throw new IllegalArgumentException(ErrorCode.MISSING_OR_INVALID_PARAM.name());
        ResponseDto result = companyDetailService.saveCompanyInfo(requestDto);
        return ResponseEntity.ok(result);
    }

    @GetMapping("")
    public ResponseEntity<?> getAllCompany(@RequestParam int page) throws IOException {
        if(page <= 0) throw new IllegalArgumentException(ErrorCode.MISSING_OR_INVALID_PARAM.name());
        ResponseDto result = companyDetailService.readAllCompanyData(page);
        return ResponseEntity.ok(result);
    }
}
