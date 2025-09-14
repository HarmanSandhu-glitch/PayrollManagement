package com.project.pms.controller;

import com.project.pms.entity.Employee;
import com.project.pms.entity.Payroll;
import com.project.pms.repository.EmployeeRepository;
import com.project.pms.repository.PayrollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PayrollRepository payrollRepository;

    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        return employee.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeRepository.save(employee);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employeeDetails) {
        Optional<Employee> employeeOptional = employeeRepository.findById(id);
        if (employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();
            employee.setEmployeeName(employeeDetails.getEmployeeName());
            employee.setEmployeeEmail(employeeDetails.getEmployeeEmail());
            employee.setEmployeeJoinDate(employeeDetails.getEmployeeJoinDate());
            employee.setEmployeeDepartment(employeeDetails.getEmployeeDepartment());
            employee.setEmployeePosition(employeeDetails.getEmployeePosition());
            return ResponseEntity.ok(employeeRepository.save(employee));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        if (employeeRepository.existsById(id)) {
            employeeRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/getPayroll")
    public ResponseEntity<List<Payroll>> getEmployeePayroll(@PathVariable Long id) {
        Optional<Employee> employeeOptional = employeeRepository.findById(id);
        if (employeeOptional.isPresent()) {
            List<Payroll> payrolls = payrollRepository.findByEmployeeEmployeeId(id);
            return ResponseEntity.ok(payrolls);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}