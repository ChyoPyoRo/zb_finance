package com.zerobase.finance.repository;

import com.zerobase.finance.entity.Dividend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DividendRepository extends JpaRepository<Dividend, Long> {
}
