package com.project.pms.service;

import com.project.pms.entity.Attendance;
import com.project.pms.entity.Employee;
import com.project.pms.repository.AttendanceRepository;
import com.project.pms.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public Attendance checkIn(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        LocalDate today = LocalDate.now();
        Attendance attendance = attendanceRepository.findByEmployeeEmployeeIdAndDate(employeeId, today)
                .orElse(new Attendance());

        attendance.setEmployee(employee);
        attendance.setDate(today);
        attendance.setCheckInTime(LocalTime.now());
        return attendanceRepository.save(attendance);
    }

    public Attendance checkOut(Long employeeId) {
        LocalDate today = LocalDate.now();
        Attendance attendance = attendanceRepository.findByEmployeeEmployeeIdAndDate(employeeId, today)
                .orElseThrow(() -> new RuntimeException("Check-in record not found for today"));
        attendance.setCheckOutTime(LocalTime.now());
        return attendanceRepository.save(attendance);
    }

    public List<Attendance> getAttendanceForEmployee(Long employeeId) {
        return attendanceRepository.findByEmployeeEmployeeId(employeeId);
    }
}