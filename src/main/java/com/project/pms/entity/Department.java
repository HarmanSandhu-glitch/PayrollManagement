package com.project.pms.entity;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "departments")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long departmentId;

    @Column(nullable = false, unique = true)
    private String departmentName;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id") // This will create a department_id column in the positions table
    private Set<Position> departmentPositions;

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Set<Position> getDepartmentPositions() {
        return departmentPositions;
    }

    public void setDepartmentPositions(Set<Position> departmentPositions) {
        this.departmentPositions = departmentPositions;
    }
}
