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
        Double deductions = (payload != null) ? payload.getOrDefault("deductions", 0.0) : 0.0;

        Double baseSalary = position.getPositionBaseSalary();
        Double experienceBonus = position.getPositionExperienceBonus();
        Double totalPay = baseSalary + experienceBonus - deductions;
        
        if (totalPay > 400000 && totalPay <= 800000) {
            deductions += totalPay * 0.05;
        } else if (totalPay > 800000 && totalPay <= 1200000) {
            deductions += totalPay * 0.10;
        } else if (totalPay > 1200000 && totalPay <= 1600000) {
            deductions += totalPay * 0.15;
        } else if (totalPay > 1600000 && totalPay <= 2000000) {
            deductions += totalPay * 0.20;
        } else if (totalPay > 2000000 && totalPay <= 2400000) {
            deductions += totalPay * 0.25;
        } else if (totalPay > 2400000) {
            deductions += totalPay * 0.30;
        }

        Payroll payroll = new Payroll();
        payroll.setEmployee(employee);
        payroll.setPayrollPayDate(new Date());
        payroll.setPayrollBaseSalary(baseSalary);
        payroll.setPayrollExperienceBonus(experienceBonus);
        payroll.setPayrollDeductions(deductions);
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
