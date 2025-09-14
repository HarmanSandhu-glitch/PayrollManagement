package com.project.pms.entity;


import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "payrolls")
public class Payroll {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long payrollId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date payrollPayDate;

    @Column(nullable = false)
    private Double payrollBaseSalary;

    @Column(nullable = false)
    private Double payrollExperienceBonus;

    @Column(nullable = false)
    private Double payrollDeductions;

    @Column(nullable = false)
    private Double payrollTotalPay;

    // Getters and Setters
    public Long getPayrollId() {
        return payrollId;
    }

    public void setPayrollId(Long payrollId) {
        this.payrollId = payrollId;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Date getPayrollPayDate() {
        return payrollPayDate;
    }

    public void setPayrollPayDate(Date payrollPayDate) {
        this.payrollPayDate = payrollPayDate;
    }

    public Double getPayrollBaseSalary() {
        return payrollBaseSalary;
    }

    public void setPayrollBaseSalary(Double payrollBaseSalary) {
        this.payrollBaseSalary = payrollBaseSalary;
    }

    public Double getPayrollExperienceBonus() {
        return payrollExperienceBonus;
    }

    public void setPayrollExperienceBonus(Double payrollExperienceBonus) {
        this.payrollExperienceBonus = payrollExperienceBonus;
    }

    public Double getPayrollDeductions() {
        return payrollDeductions;
    }

    public void setPayrollDeductions(Double payrollDeductions) {
        this.payrollDeductions = payrollDeductions;
    }

    public Double getPayrollTotalPay() {
        return payrollTotalPay;
    }

    public void setPayrollTotalPay(Double payrollTotalPay) {
        this.payrollTotalPay = payrollTotalPay;
    }
}