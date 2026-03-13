package ro.upt.ac.centrurecuperare.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.upt.ac.centrurecuperare.model.Internare;
import ro.upt.ac.centrurecuperare.model.Pacient;
import ro.upt.ac.centrurecuperare.service.InternareService;
import ro.upt.ac.centrurecuperare.service.PacientService;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/internari")
@RequiredArgsConstructor
public class InternareController {

    private final InternareService internareService;
    private final PacientService pacientService;

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
    public ResponseEntity<?> createInternare(@RequestBody Map<String, Object> payload) {
        try {
            Internare internare = new Internare();

            // Extrage pacient_id și încarcă pacientul
            Long pacientId = Long.valueOf(payload.get("pacient_id").toString());
            Pacient pacient = pacientService.getPacientById(pacientId)
                    .orElseThrow(() -> new IllegalArgumentException("Pacient cu ID " + pacientId + " nu există"));

            internare.setPacient(pacient);
            internare.setDataInternare(LocalDate.parse(payload.get("dataInternare").toString()));
            internare.setDurataEstimata(Integer.valueOf(payload.get("durataEstimata").toString()));
            internare.setPrioritate(Internare.Prioritate.valueOf(payload.get("prioritate").toString()));
            internare.setStatus(Internare.StatusInternare.IN_ASTEPTARE);

            if (payload.containsKey("diagnostic")) {
                internare.setDiagnostic(payload.get("diagnostic").toString());
            }

            Internare saved = internareService.saveInternare(internare);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Eroare: " + e.getMessage());
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
