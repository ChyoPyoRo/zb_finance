package com.zerobase.finance.service;

import com.zerobase.finance.dto.AddCompanyRequestDto;
import com.zerobase.finance.dto.ResponseDto;
import com.zerobase.finance.enums.ErrorCode;
import com.zerobase.finance.repository.CompanyDetailRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class CompanyDetailService {
    private final CompanyDetailRepository companyDetailRepository;

    public ResponseDto<?> saveCompanyInfo(AddCompanyRequestDto requestDto){
        //동일한 ticker를 가진 회사가 존재하는지 확인
        if(companyDetailRepository.findCompanyByticker(requestDto.getTicker())!=null)throw new  EntityExistsException(ErrorCode.COMPANY_DUPLICATE.name());
        //있으면 error, 없으면 회사 정보 가져오기
        //정보 가져오는데 실패해도 error
        //데이터 저장
        //성공 반환
        return ResponseDto.success();
    }
}
