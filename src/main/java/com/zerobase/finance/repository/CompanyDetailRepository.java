package com.zerobase.finance.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.zerobase.finance.entity.Company;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import static com.zerobase.finance.entity.QCompany.company;

@RestController
@RequiredArgsConstructor
public class CompanyDetailRepository {
    private final ComapnyRepository companyRepository;
    private final JPAQueryFactory queryFactory;

    public Company findCompanyByticker(String ticker) {
        return queryFactory.selectFrom(company)
                .where(company.ticker.eq(ticker))
                .fetchOne();
    }
}
