package ro.upt.ac.centrurecuperare.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.upt.ac.centrurecuperare.model.Internare;
import ro.upt.ac.centrurecuperare.service.InternareService;

import java.util.List;

@RestController
@RequestMapping("/api/internari")
@RequiredArgsConstructor
public class InternareController {

    private final InternareService internareService;

    @GetMapping
    public ResponseEntity<List<Internare>> getAllInternari() {
        return ResponseEntity.ok(internareService.getAllInternari());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Internare> getInternareById(@PathVariable Long id) {
        return internareService.getInternareById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/asteptare")
    public ResponseEntity<List<Internare>> getInternariInAsteptare() {
        return ResponseEntity.ok(internareService.getInternariInAsteptare());
    }

    @GetMapping("/nealocate")
    public ResponseEntity<List<Internare>> getInternariNealocate() {
        return ResponseEntity.ok(internareService.getInternariNealocate());
    }

    @PostMapping
    public ResponseEntity<?> createInternare(@RequestBody Internare internare) {
        try {
            Internare saved = internareService.saveInternare(internare);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateInternare(@PathVariable Long id,
                                             @RequestBody Internare internare) {
        try {
            internare.setId(id);
            Internare updated = internareService.saveInternare(internare);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteInternare(@PathVariable Long id) {
        try {
            internareService.deleteInternare(id);
            return ResponseEntity.ok("Internare ștearsă cu succes");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countInternari() {
        return ResponseEntity.ok(internareService.countInternari());
    }
}
