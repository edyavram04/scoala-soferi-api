package ro.scoalasoferi.scoala_soferi_api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.scoalasoferi.scoala_soferi_api.entities.Instructor;
import ro.scoalasoferi.scoala_soferi_api.repositories.InstructorRepository;

import java.util.List;

@RestController
@RequestMapping("/api/instructori")
public class InstructorController {

    @Autowired
    private InstructorRepository instructorRepo;

    /**
     * READ (SELECT TOȚI)
     * Răspunde la: GET http://localhost:8080/api/instructori
     * Necesar pentru a umple dropdown-ul din formularul React
     */
    @GetMapping
    public ResponseEntity<List<Instructor>> getAllInstructori() {
        // Folosim funcția noastră SQL nativă "gasesteToti"
        List<Instructor> instructori = instructorRepo.gasesteToti();
        return ResponseEntity.ok(instructori);
    }

    // Vom adăuga POST, PUT, DELETE pentru instructori mai târziu
}