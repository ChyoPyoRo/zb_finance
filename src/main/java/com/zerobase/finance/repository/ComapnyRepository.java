package com.zerobase.finance.repository;

import com.zerobase.finance.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComapnyRepository extends JpaRepository<Company, Long> {
}
