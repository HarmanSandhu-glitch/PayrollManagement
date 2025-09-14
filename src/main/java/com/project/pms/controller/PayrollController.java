package com.project.pms.controller;

import com.project.pms.entity.Employee;
import com.project.pms.entity.Payroll;
import com.project.pms.entity.Position;
import com.project.pms.repository.EmployeeRepository;
import com.project.pms.repository.PayrollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/payroll")
public class PayrollController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PayrollRepository payrollRepository;

    @PostMapping("/generate/{employeeId}")
    public ResponseEntity<Payroll> generatePayroll(@PathVariable Long employeeId, @RequestBody(required = false) Map<String, Double> payload) {
        Optional<Employee> employeeOptional = employeeRepository.findById(employeeId);

        if (employeeOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Employee employee = employeeOptional.get();
        Position position = employee.getEmployeePosition();
        Double initialDeductions = (payload != null) ? payload.getOrDefault("deductions", 0.0) : 0.0;

        Double baseSalary = position.getPositionBaseSalary();
        Double experienceBonus = position.getPositionExperienceBonus();
        Double grossPay = baseSalary + experienceBonus;
        Double taxDeductions = 0.0;

        if (grossPay > 400000 && grossPay <= 800000) {
            taxDeductions = grossPay * 0.05;
        } else if (grossPay > 800000 && grossPay <= 1200000) {
            taxDeductions = grossPay * 0.10;
        } else if (grossPay > 1200000 && grossPay <= 1600000) {
            taxDeductions = grossPay * 0.15;
        } else if (grossPay > 1600000 && grossPay <= 2000000) {
            taxDeductions = grossPay * 0.20;
        } else if (grossPay > 2000000 && grossPay <= 2400000) {
            taxDeductions = grossPay * 0.25;
        } else if (grossPay > 2400000) {
            taxDeductions = grossPay * 0.30;
        }

        Double totalDeductions = initialDeductions + taxDeductions;
        Double totalPay = grossPay - totalDeductions;

        Payroll payroll = new Payroll();
        payroll.setEmployee(employee);
        payroll.setPayrollPayDate(new Date());
        payroll.setPayrollBaseSalary(baseSalary);
        payroll.setPayrollExperienceBonus(experienceBonus);
        payroll.setPayrollDeductions(totalDeductions);
        payroll.setPayrollTotalPay(totalPay);

        Payroll savedPayroll = payrollRepository.save(payroll);
        return ResponseEntity.ok(savedPayroll);
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<Payroll>> getPayrollsForEmployee(@PathVariable Long employeeId) {
        if (!employeeRepository.existsById(employeeId)) {
            return ResponseEntity.notFound().build();
        }
        List<Payroll> payrolls = payrollRepository.findByEmployeeEmployeeId(employeeId);
        return ResponseEntity.ok(payrolls);
    }
}