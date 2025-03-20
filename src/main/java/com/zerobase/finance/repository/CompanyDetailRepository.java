package com.zerobase.finance.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.zerobase.finance.dto.CompanyDto;
import com.zerobase.finance.dto.ReadFinanceInfoResponseDto;
import com.zerobase.finance.entity.Company;
import com.zerobase.finance.entity.Dividend;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.zerobase.finance.entity.QCompany.company;
import static com.zerobase.finance.entity.QDividend.dividend;

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

    public Page<CompanyDto> readAllCompany(Pageable pageable) {
        List<CompanyDto> companyList = queryFactory
                .select(Projections.bean(CompanyDto.class,
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
    @Cacheable(key = "#companyName", value = "finance")
    public Company findCompanyByCompanyName(String companyName) {
        return queryFactory.selectFrom(company).where(company.name.eq(companyName)).fetchOne();
    }
    @Cacheable(key = "#company.ticker", value = "finance")
    public List<ReadFinanceInfoResponseDto.DividendDto> dividendByCompany(Company company) {
        return queryFactory.select(Projections.constructor(ReadFinanceInfoResponseDto.DividendDto.class,
                dividend.date,dividend.dividends.as("dividend")
                )).from(dividend).where(dividend.companyId.eq(company.getId())).fetch();
    }

    public List<String> searchCompanyByPrefix(String prefix) {
        return queryFactory.select(company.name)
                .from(company)
                .where(
                        company.name.startsWith(prefix)
                                .or(company.name.containsIgnoreCase(prefix))
                                .or(company.name.endsWith(prefix))
                )
                .limit(10)
                .fetch();

    }
}
