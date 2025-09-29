package com.project.pms.repository;

import com.project.pms.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    Optional<Attendance> findByEmployeeEmployeeIdAndDate(Long employeeId, LocalDate date);
    List<Attendance> findByEmployeeEmployeeId(Long employeeId);
}