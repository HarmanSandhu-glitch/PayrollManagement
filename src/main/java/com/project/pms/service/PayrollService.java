package com.project.pms.service;

import com.project.pms.entity.Employee;
import com.project.pms.entity.Payroll;
import com.project.pms.entity.Position;
import com.project.pms.repository.PayrollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class PayrollService {

    @Autowired
    private PayrollRepository payrollRepository;

    @Autowired
    private EmployeeService employeeService;

    public Payroll generatePayroll(Long employeeId, Map<String, Double> payload) {
        Employee employee = employeeService.getEmployeeById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        Position position = employee.getEmployeePosition();
        Double initialDeductions = (payload != null) ? payload.getOrDefault("deductions", 0.0) : 0.0;

        Double baseSalary = position.getPositionBaseSalary();
        Double experienceBonus = position.getPositionExperienceBonus();
        Double grossPay = baseSalary + experienceBonus;
        Double taxDeductions = calculateTax(grossPay);
        Double totalDeductions = initialDeductions + taxDeductions;
        Double totalPay = grossPay - totalDeductions;

        Payroll payroll = new Payroll();
        payroll.setEmployee(employee);
        payroll.setPayrollPayDate(new Date());
        payroll.setPayrollBaseSalary(baseSalary);
        payroll.setPayrollExperienceBonus(experienceBonus);
        payroll.setPayrollDeductions(totalDeductions);
        payroll.setPayrollTotalPay(totalPay);

        return payrollRepository.save(payroll);
    }

    public List<Payroll> getPayrollsForEmployee(Long employeeId) {
        return payrollRepository.findByEmployeeEmployeeId(employeeId);
    }

    private Double calculateTax(Double grossPay) {
        if (grossPay > 2400000) return grossPay * 0.30;
        if (grossPay > 2000000) return grossPay * 0.25;
        if (grossPay > 1600000) return grossPay * 0.20;
        if (grossPay > 1200000) return grossPay * 0.15;
        if (grossPay > 800000) return grossPay * 0.10;
        if (grossPay > 400000) return grossPay * 0.05;
        return 0.0;
    }
}