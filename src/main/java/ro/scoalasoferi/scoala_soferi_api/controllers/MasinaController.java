/** Controller REST pentru gestionarea parcului auto al școlii.
 * Permite evidența vehiculelor și asocierea lor cu categoriile de permis.
 * @author Avram Eduard-Andrei
 * @version 11 Ianuarie 2026
 */
package ro.scoalasoferi.scoala_soferi_api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.scoalasoferi.scoala_soferi_api.entities.Masina;
import ro.scoalasoferi.scoala_soferi_api.repositories.MasinaRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/masini")

public class MasinaController {

    @Autowired
    private MasinaRepository masinaRepo;

    public static class MasinaRequest {
        public String nrInmatriculare;
        public String marca;
        public String model;
        public Long idCategorie;
    }


    @GetMapping
    public ResponseEntity<List<Masina>> getAll() {
        return ResponseEntity.ok(masinaRepo.gasesteToate());
    }

    @GetMapping("/{nr}")
    public ResponseEntity<?> getOne(@PathVariable String nr) {
        Optional<Masina> m = masinaRepo.gasesteDupaId(nr);
        if (m.isPresent()) return ResponseEntity.ok(m.get());
        else return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody MasinaRequest req) {
        masinaRepo.adaugaMasinaNoua(
                req.nrInmatriculare,
                req.marca,
                req.model,
                req.idCategorie
        );
        return ResponseEntity.ok("Mașină adăugată!");
    }

    @PutMapping("/{nr}")
    public ResponseEntity<?> update(@PathVariable String nr, @RequestBody MasinaRequest req) {
        masinaRepo.actualizeazaMasina(
                nr,
                req.marca,
                req.model,
                req.idCategorie
        );
        return ResponseEntity.ok("Mașină actualizată!");
    }

    @DeleteMapping("/{nr}")
    public ResponseEntity<?> delete(@PathVariable String nr) {
        try {
            masinaRepo.stergeDupaId(nr);
            return ResponseEntity.ok("Mașină ștearsă!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Eroare la ștergere.");
        }
    }
}