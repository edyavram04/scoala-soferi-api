package ro.scoalasoferi.scoala_soferi_api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.scoalasoferi.scoala_soferi_api.entities.Instructor;
import ro.scoalasoferi.scoala_soferi_api.repositories.InstructorRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/instructori")

public class InstructorController {

    @Autowired
    private InstructorRepository instructorRepo;

    // 1. GET ALL
    @GetMapping
    public ResponseEntity<List<Instructor>> getAllInstructori() {
        // Aici returnam lista
        return ResponseEntity.ok(instructorRepo.gasesteToti());
    }

    // 2. GET ONE (by ID)
    @GetMapping("/{id}")
    public ResponseEntity<?> getInstructorById(@PathVariable Integer id) {
        Optional<Instructor> instr = instructorRepo.gasesteDupaId(id);

        if (instr.isPresent()) {
            // CAZUL 1: Găsit -> Returnăm obiectul
            return ResponseEntity.ok(instr.get());
        } else {
            // CAZUL 2: Negăsit -> Returnăm eroare 404
            return ResponseEntity.notFound().build();
        }
        // Eroarea ta de la linia 24 era probabil aici (lipsea un return)
    }

    // 3. CREATE
    @PostMapping
    public ResponseEntity<?> createInstructor(@RequestBody Instructor instructor) {
        instructorRepo.adaugaInstructorNou(
                instructor.getNume(),
                instructor.getPrenume(),
                instructor.getCnp(),
                instructor.getTelefon()
        );
        // Trebuie neapărat să returnăm ceva
        return ResponseEntity.ok("Instructor adăugat cu succes!");
    }

    // 4. UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<?> updateInstructor(@PathVariable Integer id, @RequestBody Instructor instructor) {
        instructorRepo.actualizeazaInstructor(
                id,
                instructor.getNume(),
                instructor.getPrenume(),
                instructor.getCnp(),
                instructor.getTelefon()
        );
        // Trebuie neapărat să returnăm ceva
        return ResponseEntity.ok("Instructor actualizat!");
    }

    // 5. DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteInstructor(@PathVariable Integer id) {
        try {
            instructorRepo.stergeDupaId(id);
            // Cazul fericit
            return ResponseEntity.ok("Instructor șters!");
        } catch (Exception e) {
            // Cazul nefericit
            return ResponseEntity.badRequest().body("Nu se poate șterge instructorul.");
        }
        // Eroarea ta de la linia 45 era probabil aici
    }
}