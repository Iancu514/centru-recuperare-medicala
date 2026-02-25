package ro.upt.ac.centrurecuperare.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.upt.ac.centrurecuperare.model.Pacient;
import ro.upt.ac.centrurecuperare.service.PacientService;

import java.util.List;

@RestController
@RequestMapping("/api/pacienti")
@RequiredArgsConstructor
public class PacientController {

    private final PacientService pacientService;

    //GET /api/pacienti - Returneaza toti pacientii

    @GetMapping
    public ResponseEntity<List<Pacient>> getAllPacienti() {
        List<Pacient> pacienti = pacientService.getAllPacienti();
        return ResponseEntity.ok(pacienti);
    }

    //GET /api/pacienti/{id} - Returneaza un pacient dupa ID
    @GetMapping("/{id}")
    public ResponseEntity<Pacient> getPacientById(@PathVariable Long id) {
        return pacientService.getPacientById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //GET /api/pacienti/cnp/{cnp} - Returneaza un pacient dupa CNP
    @GetMapping("/cnp/{cnp}")
    public ResponseEntity<Pacient> getPacientByCnp(@PathVariable String cnp) {
        return pacientService.getPacientByCnp(cnp)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * POST /api/pacienti - Creeaza un pacient nou
     */
    @PostMapping
    public ResponseEntity<?> createPacient(@RequestBody Pacient pacient) {
        try {
            Pacient savedPacient = pacientService.savePacient(pacient);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedPacient);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //PUT /api/pacienti/{id} - Actualizeaza un pacient
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePacient(@PathVariable Long id, @RequestBody Pacient pacient) {
        try {
            pacient.setId(id);
            Pacient updatedPacient = pacientService.savePacient(pacient);
            return ResponseEntity.ok(updatedPacient);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //DELETE /api/pacienti/{id} - Sterge un pacient
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePacient(@PathVariable Long id) {
        try {
            pacientService.deletePacient(id);
            return ResponseEntity.ok().body("Pacient șters cu succes");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //GET /api/pacienti/count - Returneaza numarul total de pacienti
    @GetMapping("/count")
    public ResponseEntity<Long> countPacienti() {
        return ResponseEntity.ok(pacientService.countPacienti());
    }
}