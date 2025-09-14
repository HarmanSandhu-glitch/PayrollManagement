package com.project.pms.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeId;

    @Column(nullable = false)
    private String employeeName;

    @Column(nullable = false, unique = true)
    private String employeeEmail;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date employeeJoinDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "position_id", nullable = false)
    private Position employeePosition;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "department_id", nullable = false)
    private Department employeeDepartment;

    // Getters and Setters
    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }

    public Date getEmployeeJoinDate() {
        return employeeJoinDate;
    }

    public void setEmployeeJoinDate(Date employeeJoinDate) {
        this.employeeJoinDate = employeeJoinDate;
    }

    public Position getEmployeePosition() {
        return employeePosition;
    }

    public void setEmployeePosition(Position employeePosition) {
        this.employeePosition = employeePosition;
    }

    public Department getEmployeeDepartment() {
        return employeeDepartment;
    }

    public void setEmployeeDepartment(Department employeeDepartment) {
        this.employeeDepartment = employeeDepartment;
    }
}
