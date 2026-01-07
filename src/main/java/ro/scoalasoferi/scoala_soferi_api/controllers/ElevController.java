package ro.scoalasoferi.scoala_soferi_api.controllers;

import jakarta.validation.Valid; // <--- ESENȚIAL PENTRU VALIDARE
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

    // --- Funcție ajutătoare: Transformă "popescu" în "Popescu" ---
    private String capitalize(String text) {
        if (text == null || text.isEmpty()) return text;
        String[] words = text.split(" ");
        StringBuilder formatted = new StringBuilder();
        for (String word : words) {
            if (word.length() > 0) {
                formatted.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1).toLowerCase())
                        .append(" ");
            }
        }
        return formatted.toString().trim();
    }

    // 1. GET ALL
    @GetMapping
    public ResponseEntity<List<Elev>> getAllElevi() {
        List<Elev> elevi = elevRepo.gasesteToti();
        return ResponseEntity.ok(elevi);
    }

    // 2. GET ONE
    @GetMapping("/{id}")
    public ResponseEntity<Elev> getElevById(@PathVariable Integer id) {
        Optional<Elev> elev = elevRepo.gasesteDupaId(id);
        return elev.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 3. CREATE (Cu Validare și Formatare)
    @PostMapping
    // @Valid activează regulile din Elev.java (@NotBlank, @Pattern, etc.)
    public ResponseEntity<?> createElev(@Valid @RequestBody Elev elev) {
        try {
            // Formatăm numele înainte să le trimitem la procedura stocată
            String numeFormatat = capitalize(elev.getNume());
            String prenumeFormatat = capitalize(elev.getPrenume());

            elevRepo.adaugaElevNou(
                    numeFormatat,
                    prenumeFormatat,
                    elev.getTelefon(),
                    (elev.getInstructor() != null) ? elev.getInstructor().getId() : null
            );
            return ResponseEntity.status(HttpStatus.CREATED).body("Elev creat cu succes!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Eroare la crearea elevului: " + e.getMessage());
        }
    }

    // 4. UPDATE (Cu Validare și Formatare)
    @PutMapping("/{id}")
    public ResponseEntity<?> updateElev(@PathVariable Integer id, @Valid @RequestBody Elev elevDetalii) {
        try {
            // Formatăm numele și la actualizare
            String numeFormatat = capitalize(elevDetalii.getNume());
            String prenumeFormatat = capitalize(elevDetalii.getPrenume());

            elevRepo.actualizeazaElev(
                    id,
                    numeFormatat,
                    prenumeFormatat,
                    elevDetalii.getTelefon(),
                    (elevDetalii.getInstructor() != null) ? elevDetalii.getInstructor().getId() : null
            );
            return ResponseEntity.ok("Elev actualizat!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Eroare la actualizarea elevului: " + e.getMessage());
        }
    }

    // 5. DELETE (Cu Transactional - păstrat exact cum l-ai făcut tu)
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> deleteElev(@PathVariable Integer id) {
        try {
            // Pas 1: Ștergem înscrierile asociate (cursurile elevului)
            elevCursRepo.stergeDupaIdElev(id);

            // Pas 2: Ștergem elevul propriu-zis
            elevRepo.stergeDupaId(id);

            return ResponseEntity.ok("Elev și înscrierile asociate au fost șterse!");

        } catch (Exception e) {
            // Dacă oricare pas eșuează, @Transactional va anula totul (rollback)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Eroare la ștergerea elevului: " + e.getMessage());
        }
    }
}