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
@RequestMapping("/api/elevi") // Totul de aici va fi sub /api/elevi
public class ElevController {

    @Autowired
    private ElevRepository elevRepo;

    @Autowired
    private ElevCursRepository elevCursRepo;

    /**
     * READ (SELECT TOȚI)
     * Răspunde la: GET http://localhost:8080/api/elevi
     */
    @GetMapping
    public ResponseEntity<List<Elev>> getAllElevi() {
        // Folosim funcția noastră SQL nativă "gasesteToti"
        List<Elev> elevi = elevRepo.gasesteToti();
        return ResponseEntity.ok(elevi); // Trimite lista ca JSON
    }

    /**
     * READ (SELECT UNUL)
     * Răspunde la: GET http://localhost:8080/api/elevi/5
     */
    @GetMapping("/{id}")
    public ResponseEntity<Elev> getElevById(@PathVariable Integer id) {
        // Folosim "gasesteDupaId"
        Optional<Elev> elev = elevRepo.gasesteDupaId(id);

        return elev.map(ResponseEntity::ok) // Dacă îl găsește, trimite-l
                .orElse(ResponseEntity.notFound().build()); // Altfel, trimite eroare 404
    }

    /**
     * CREATE (INSERT)
     * Răspunde la: POST http://localhost:8080/api/elevi
     * Se așteaptă la un JSON cu datele elevului în @RequestBody
     *
     * NOTĂ: Pentru a funcționa corect, clasa ta Elev.java
     * TREBUIE să aibă un constructor care primește parametrii.
     * Adaugă în Elev.java:
     * public Elev(String nume, String prenume, String telefon, Instructor instructor) { ... }
     */
    @PostMapping
    public ResponseEntity<?> createElev(@RequestBody Elev elev) {
        // Aici e o problemă cu stilul SQL nativ.
        // Funcția "adaugaElevNou" cere parametri separați, dar @RequestBody ne dă un obiect 'Elev'
        // Cea mai simplă soluție e să extragem manual:
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

    /**
     * UPDATE
     * Răspunde la: PUT http://localhost:8080/api/elevi/5
     */
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

    /**
     * DELETE
     * Răspunde la: DELETE http://localhost:8080/api/elevi/5
     */
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> deleteElev(@PathVariable Integer id) {
        try {
            // 5. PASUL 1: Șterge "copiii" (înscrierile la cursuri)
            elevCursRepo.stergeDupaIdElev(id);

            // 6. PASUL 2: Șterge "părintele" (elevul)
            elevRepo.stergeDupaId(id);

            return ResponseEntity.ok("Elev și înscrierile asociate au fost șterse!");

        } catch (Exception e) {
            // Dacă oricare pas eșuează, @Transactional va anula totul
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Eroare la ștergerea elevului: " + e.getMessage());
        }
    }
}