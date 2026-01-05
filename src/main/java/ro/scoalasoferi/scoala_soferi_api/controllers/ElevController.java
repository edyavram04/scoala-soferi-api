package ro.scoalasoferi.scoala_soferi_api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ro.scoalasoferi.scoala_soferi_api.entities.Elev;
import ro.scoalasoferi.scoala_soferi_api.repositories.ElevCursRepository;
import ro.scoalasoferi.scoala_soferi_api.repositories.ElevRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/elevi")

public class ElevController {

    @Autowired
    private ElevRepository elevRepo;

    @Autowired
    private ElevCursRepository elevCursRepo;


    @GetMapping
    public ResponseEntity<List<Elev>> getAllElevi() {
        List<Elev> elevi = elevRepo.gasesteToti();
        return ResponseEntity.ok(elevi);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Elev> getElevById(@PathVariable Integer id) {
        Optional<Elev> elev = elevRepo.gasesteDupaId(id);

        return elev.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PostMapping
    public ResponseEntity<?> createElev(@RequestBody Elev elev) {

        try {
            elevRepo.adaugaElevNou(
                    elev.getNume(),
                    elev.getPrenume(),
                    elev.getTelefon(),
                    (elev.getInstructor() != null) ? elev.getInstructor().getId() : null
            );
            return ResponseEntity.status(HttpStatus.CREATED).body("Elev creat cu succes!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Eroare la crearea elevului: " + e.getMessage());
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateElev(@PathVariable Integer id, @RequestBody Elev elevDetalii) {
        try {
            elevRepo.actualizeazaElev(
                    id,
                    elevDetalii.getNume(),
                    elevDetalii.getPrenume(),
                    elevDetalii.getTelefon(),
                    (elevDetalii.getInstructor() != null) ? elevDetalii.getInstructor().getId() : null
            );
            return ResponseEntity.ok("Elev actualizat!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Eroare la actualizarea elevului: " + e.getMessage());
        }
    }


    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> deleteElev(@PathVariable Integer id) {
        try {

            elevCursRepo.stergeDupaIdElev(id);


            elevRepo.stergeDupaId(id);

            return ResponseEntity.ok("Elev și înscrierile asociate au fost șterse!");

        } catch (Exception e) {
            // Dacă oricare pas eșuează, @Transactional va anula totul
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Eroare la ștergerea elevului: " + e.getMessage());
        }
    }
}