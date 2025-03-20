package com.zerobase.finance.service;

import com.zerobase.finance.dto.AddCompanyRequestDto;
import com.zerobase.finance.dto.ResponseDto;
import com.zerobase.finance.dto.ScrapingDataDto;
import com.zerobase.finance.entity.Company;
import com.zerobase.finance.entity.Dividend;
import com.zerobase.finance.enums.ErrorCode;
import com.zerobase.finance.exception.ScrappiingException;
import com.zerobase.finance.repository.CompanyDetailRepository;
import com.zerobase.finance.utils.ScrappingUtil;
import com.zerobase.finance.utils.UserUtils;
import jakarta.persistence.EntityExistsException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class CompanyDetailService {
    private final CompanyDetailRepository companyDetailRepository;
    private final ScrappingUtil scrappingUtil;

    

    public ResponseDto<?> saveCompanyInfo(AddCompanyRequestDto requestDto) throws IOException, ScrappiingException, IllegalAccessException {
        if(!UserUtils.isAdmin()) throw new IllegalAccessException(ErrorCode.UNAUTHORIZED.name());
        //동일한 ticker를 가진 회사가 존재하는지 확인
        if(companyDetailRepository.findCompanyByticker(requestDto.getTicker())!=null)throw new  EntityExistsException(ErrorCode.COMPANY_DUPLICATE.name());
        //있으면 error, 없으면 회사 정보 가져오기
        List<ScrapingDataDto> scrapingDataDto = scrappingUtil.getScrapingData(requestDto.getTicker());
        //데이터 저장
        Company company = Company.builder()
                .id(UUID.randomUUID())
                .name(scrapingDataDto.get(0).getCompanyName())
                .ticker(scrapingDataDto.get(0).getCompanyTicker())
                .build();
        companyDetailRepository.saveCompany(company);
        saveData(company, scrapingDataDto);
        //성공 반환
        return ResponseDto.success();
    }


    private void saveData(Company company, List<ScrapingDataDto> scrapingDataDtoList){
        List<Dividend> dividends = scrapingDataDtoList.stream()
                .map(data->Dividend.builder().companyId(company.getId()).date(data.getDate()).dividend(data.getDividend()).build()).toList();
        companyDetailRepository.saveAllDividend(dividends);
    }
}
