package com.project.pms.service;

import com.project.pms.entity.Employee;
import com.project.pms.repository.EmployeeRepository;
import com.project.pms.repository.PayrollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PayrollRepository payrollRepository;

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Transactional
    public void deleteEmployee(Long id) {
        payrollRepository.deleteAll(payrollRepository.findByEmployeeEmployeeId(id));
        employeeRepository.deleteById(id);
    }
}