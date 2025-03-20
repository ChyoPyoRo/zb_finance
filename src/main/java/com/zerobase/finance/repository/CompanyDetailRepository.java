package com.zerobase.finance.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.zerobase.finance.dto.ReadAllCompanyResponseDto;
import com.zerobase.finance.entity.Company;
import com.zerobase.finance.entity.Dividend;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.zerobase.finance.entity.QCompany.company;

@RestController
@RequiredArgsConstructor
public class CompanyDetailRepository {
    private final ComapnyRepository companyRepository;
    private final DividendRepository dividendRepository;
    private final JPAQueryFactory queryFactory;

    public Company findCompanyByticker(String ticker) {
        return queryFactory.selectFrom(company)
                .where(company.ticker.eq(ticker))
                .fetchOne();
    }

    public void saveCompany(Company company) {
        companyRepository.save(company);
    }

    public void saveAllDividend(List<Dividend> dividends) {
        dividendRepository.saveAll(dividends);
    }

    public List<Company> getAllCompanyList() {
        return queryFactory.selectFrom(company).fetch();
    }

    public void deleteAllDividend() {
        dividendRepository.deleteAll();
    }

    public Page<ReadAllCompanyResponseDto> readAllCompany(Pageable pageable) {
        List<ReadAllCompanyResponseDto> companyList = queryFactory
                .select(Projections.bean(ReadAllCompanyResponseDto.class,
                        company.name.as("companyName"),
                        company.ticker.as("companyTicker")
                        ))
                .from(company)
                .orderBy(company.name.asc())
                .offset(pageable.getPageNumber())
                .limit(pageable.getPageSize())
                .fetch();
        long total = queryFactory.selectFrom(company).fetch().size();
        return new PageImpl<>(companyList, pageable, total);
    }
}
