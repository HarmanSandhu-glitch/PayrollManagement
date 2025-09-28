package com.project.pms.controller;

import com.project.pms.entity.Position;
import com.project.pms.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/positions")
public class PositionController {

    @Autowired
    private PositionService positionService;

    @GetMapping
    public List<Position> getAllPositions() {
        return positionService.getAllPositions();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Position> getPositionById(@PathVariable Long id) {
        return positionService.getPositionById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Position createPosition(@Valid @RequestBody Position position) {
        return positionService.savePosition(position);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Position> updatePosition(@PathVariable Long id, @Valid @RequestBody Position positionDetails) {
        return positionService.getPositionById(id)
                .map(position -> {
                    position.setPositionTitle(positionDetails.getPositionTitle());
                    position.setPositionExperienceBonus(positionDetails.getPositionExperienceBonus());
                    position.setPositionBaseSalary(positionDetails.getPositionBaseSalary());
                    return ResponseEntity.ok(positionService.savePosition(position));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePosition(@PathVariable Long id) {
        if (positionService.getPositionById(id).isPresent()) {
            positionService.deletePosition(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}