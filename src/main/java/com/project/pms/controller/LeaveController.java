package com.project.pms.controller;

import com.project.pms.entity.Leave;
import com.project.pms.service.LeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leaves")
public class LeaveController {

    @Autowired
    private LeaveService leaveService;

    @PostMapping("/apply/{employeeId}")
    public ResponseEntity<Leave> applyForLeave(@PathVariable Long employeeId, @RequestBody Leave leave) {
        return ResponseEntity.ok(leaveService.applyForLeave(employeeId, leave));
    }

    @PutMapping("/update/{leaveId}")
    public ResponseEntity<Leave> updateLeaveStatus(@PathVariable Long leaveId, @RequestParam Leave.LeaveStatus status) {
        return ResponseEntity.ok(leaveService.updateLeaveStatus(leaveId, status));
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<Leave>> getLeavesForEmployee(@PathVariable Long employeeId) {
        return ResponseEntity.ok(leaveService.getLeavesForEmployee(employeeId));
    }

    @GetMapping
    public ResponseEntity<List<Leave>> getAllLeaveRequests() {
        return ResponseEntity.ok(leaveService.getAllLeaveRequests());
    }
}