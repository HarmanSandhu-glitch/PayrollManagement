package com.project.pms.service;

import com.project.pms.entity.Employee;
import com.project.pms.entity.Leave;
import com.project.pms.repository.EmployeeRepository;
import com.project.pms.repository.LeaveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaveService {

    @Autowired
    private LeaveRepository leaveRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public Leave applyForLeave(Long employeeId, Leave leave) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        leave.setEmployee(employee);
        return leaveRepository.save(leave);
    }

    public Leave updateLeaveStatus(Long leaveId, Leave.LeaveStatus status) {
        Leave leave = leaveRepository.findById(leaveId)
                .orElseThrow(() -> new RuntimeException("Leave record not found"));
        leave.setStatus(status);
        return leaveRepository.save(leave);
    }

    public List<Leave> getLeavesForEmployee(Long employeeId) {
        return leaveRepository.findByEmployeeEmployeeId(employeeId);
    }

    public List<Leave> getAllLeaveRequests() {
        return leaveRepository.findAll();
    }
}