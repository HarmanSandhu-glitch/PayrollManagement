package com.project.pms.controller;

import com.project.pms.entity.Employee;
import com.project.pms.entity.Payroll;
import com.project.pms.service.EmployeeService;
import com.project.pms.service.PayrollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private PayrollService payrollService;

    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Optional<Employee> employee = employeeService.getEmployeeById(id);
        return employee.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Employee createEmployee(@Valid @RequestBody Employee employee) {
        return employeeService.saveEmployee(employee);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @Valid @RequestBody Employee employeeDetails) {
        return employeeService.getEmployeeById(id)
                .map(employee -> {
                    employee.setEmployeeName(employeeDetails.getEmployeeName());
                    employee.setEmployeeEmail(employeeDetails.getEmployeeEmail());
                    employee.setEmployeeJoinDate(employeeDetails.getEmployeeJoinDate());
                    employee.setEmployeeDepartment(employeeDetails.getEmployeeDepartment());
                    employee.setEmployeePosition(employeeDetails.getEmployeePosition());
                    return ResponseEntity.ok(employeeService.saveEmployee(employee));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        if (employeeService.getEmployeeById(id).isPresent()) {
            employeeService.deleteEmployee(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/getPayroll")
    public ResponseEntity<List<Payroll>> getEmployeePayroll(@PathVariable Long id) {
        return employeeService.getEmployeeById(id)
                .map(employee -> ResponseEntity.ok(payrollService.getPayrollsForEmployee(id)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}