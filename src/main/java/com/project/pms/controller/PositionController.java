package com.project.pms.controller;

import com.project.pms.entity.Position;
import com.project.pms.repository.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/positions")
public class PositionController {

    @Autowired
    private PositionRepository positionRepository;

    @GetMapping
    public List<Position> getAllPositions() {
        return positionRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Position> getPositionById(@PathVariable Long id) {
        Optional<Position> position = positionRepository.findById(id);
        return position.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Position createPosition(@RequestBody Position position) {
        return positionRepository.save(position);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Position> updatePosition(@PathVariable Long id, @RequestBody Position positionDetails) {
        Optional<Position> positionOptional = positionRepository.findById(id);
        if (positionOptional.isPresent()) {
            Position position = positionOptional.get();
            position.setPositionTitle(positionDetails.getPositionTitle());
            position.setPositionExperienceBonus(positionDetails.getPositionExperienceBonus());
            return ResponseEntity.ok(positionRepository.save(position));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePosition(@PathVariable Long id) {
        if (positionRepository.existsById(id)) {
            positionRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
