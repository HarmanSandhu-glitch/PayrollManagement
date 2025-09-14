package com.project.pms.repository;

import com.project.pms.entity.Payroll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PayrollRepository extends JpaRepository<Payroll, Long> {
    List<com.project.pms.entity.Payroll> findByEmployeeEmployeeId(Long employeeId);
}

