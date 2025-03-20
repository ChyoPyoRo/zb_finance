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
@RequiredArgsConstructor
public class CompanyController{
    private final CompanyDetailService companyDetailService;

    @PostMapping("/company")
    public ResponseEntity<?> addNewCompany(@RequestBody AddCompanyRequestDto requestDto) throws IOException, IllegalAccessException {
        if(!requestDto.isValid()) throw new IllegalArgumentException(ErrorCode.MISSING_OR_INVALID_PARAM.name());
        ResponseDto result = companyDetailService.saveCompanyInfo(requestDto);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/company")
    public ResponseEntity<?> getAllCompany(@RequestParam int page) throws IOException {
        if(page <= 0) throw new IllegalArgumentException(ErrorCode.MISSING_OR_INVALID_PARAM.name());
        ResponseDto result = companyDetailService.readAllCompanyData(page);
        return ResponseEntity.ok(result);
    }
    @GetMapping("/finance/dividend/{companyName}")
    public ResponseEntity<?> getDividend(@PathVariable String companyName) throws IllegalAccessException {
        ResponseDto<?> result = companyDetailService.getfinanceDtail(companyName);
        return ResponseEntity.ok(result);
    }
    @GetMapping("/company/autocomplete")
    public ResponseEntity<?> getCompanyAutocomplete(@RequestParam String prefix) throws IllegalAccessException {
        ResponseDto<?> result = companyDetailService.getSearchWord(prefix);
        return ResponseEntity.ok(result);
    }
    @DeleteMapping("/company/{ticker}")
    public ResponseEntity<?> deleteCompany(@PathVariable String ticker) throws IOException, IllegalAccessException {
        ResponseDto<?> result = companyDetailService.deleteCompanyByTicker(ticker);
        return ResponseEntity.ok(result);
    }
}
