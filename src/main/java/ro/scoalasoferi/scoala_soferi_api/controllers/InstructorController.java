package ro.scoalasoferi.scoala_soferi_api.controllers;

import jakarta.validation.Valid; // <--- ESENȚIAL PENTRU VALIDARE
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.scoalasoferi.scoala_soferi_api.entities.Instructor;
import ro.scoalasoferi.scoala_soferi_api.repositories.InstructorRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/instructori")
// Dacă ai probleme de conexiune cu React, poți decomenta linia de mai jos:
// @CrossOrigin(origins = "http://localhost:3000")
public class InstructorController {

    @Autowired
    private InstructorRepository instructorRepo;

    // --- Funcție ajutătoare: Transformă "popescu" în "Popescu" ---
    private String capitalize(String text) {
        if (text == null || text.isEmpty()) return text;

        // Împarte textul după spațiu (pentru nume duble gen "Ana Maria")
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
    public ResponseEntity<List<Instructor>> getAllInstructori() {
        return ResponseEntity.ok(instructorRepo.gasesteToti());
    }

    // 2. GET ONE (by ID)
    @GetMapping("/{id}")
    public ResponseEntity<?> getInstructorById(@PathVariable Integer id) {
        Optional<Instructor> instr = instructorRepo.gasesteDupaId(id);

        if (instr.isPresent()) {
            return ResponseEntity.ok(instr.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 3. CREATE (Cu Validare și Formatare)
    @PostMapping
    // @Valid activează regulile din Instructor.java (@NotBlank, @Size etc.)
    // Dacă datele sunt greșite, Spring aruncă automat eroare 400 Bad Request
    public ResponseEntity<?> createInstructor(@Valid @RequestBody Instructor instructor) {

        // Formatăm numele înainte să le trimitem la baza de date
        String numeFormatat = capitalize(instructor.getNume());
        String prenumeFormatat = capitalize(instructor.getPrenume());

        // Apelăm metoda ta custom din Repository
        instructorRepo.adaugaInstructorNou(
                numeFormatat,
                prenumeFormatat,
                instructor.getCnp(),
                instructor.getTelefon()
        );

        return ResponseEntity.ok("Instructor adăugat cu succes!");
    }

    // 4. UPDATE (Cu Validare și Formatare)
    @PutMapping("/{id}")
    public ResponseEntity<?> updateInstructor(@PathVariable Integer id, @Valid @RequestBody Instructor instructor) {

        // Formatăm și la editare
        String numeFormatat = capitalize(instructor.getNume());
        String prenumeFormatat = capitalize(instructor.getPrenume());

        // Apelăm metoda ta custom de update
        instructorRepo.actualizeazaInstructor(
                id,
                numeFormatat,
                prenumeFormatat,
                instructor.getCnp(),
                instructor.getTelefon()
        );

        return ResponseEntity.ok("Instructor actualizat!");
    }

    // 5. DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteInstructor(@PathVariable Integer id) {
        try {
            instructorRepo.stergeDupaId(id);
            return ResponseEntity.ok("Instructor șters!");
        } catch (Exception e) {
            // Dacă nu se poate șterge (ex: are elevi alocați), trimitem mesaj de eroare
            return ResponseEntity.badRequest().body("Nu se poate șterge instructorul. Verificați dacă are elevi sau mașini alocate.");
        }
    }
}