package com.project.pms.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "positions")
public class Position {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long positionId;

    @Column(nullable = false)
    private String positionTitle;

    @Column(nullable = false)
    private Double positionExperienceBonus;

    // Getters and Setters
    public Long getPositionId() {
        return positionId;
    }

    public void setPositionId(Long positionId) {
        this.positionId = positionId;
    }

    public String getPositionTitle() {
        return positionTitle;
    }

    public void setPositionTitle(String positionTitle) {
        this.positionTitle = positionTitle;
    }

    public Double getPositionExperienceBonus() {
        return positionExperienceBonus;
    }

    public void setPositionExperienceBonus(Double positionExperienceBonus) {
        this.positionExperienceBonus = positionExperienceBonus;
    }
}
