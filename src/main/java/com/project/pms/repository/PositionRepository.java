package com.project.pms.repository;


import com.project.pms.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionRepository extends JpaRepository<com.project.pms.entity.Position, Long> {
}
