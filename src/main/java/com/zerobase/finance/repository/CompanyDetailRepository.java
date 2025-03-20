package com.zerobase.finance.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.zerobase.finance.entity.Company;
import com.zerobase.finance.entity.Dividend;
import lombok.RequiredArgsConstructor;
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
}
