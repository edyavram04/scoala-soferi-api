/** Controller pentru gestionarea ofertei educaționale (tipuri de cursuri).
 * Furnizează lista cursurilor disponibile pentru procesul de înscriere.
 * @author Avram Eduard-Andrei
 * @version 11 Ianuarie 2026
 */
package ro.scoalasoferi.scoala_soferi_api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.scoalasoferi.scoala_soferi_api.entities.Curs;
import ro.scoalasoferi.scoala_soferi_api.repositories.CursRepository;

import java.util.List;

@RestController
@RequestMapping("/api/cursuri")

public class CursController {

    @Autowired
    private CursRepository cursRepo;

    @GetMapping
    public ResponseEntity<List<Curs>> getAllCursuri() {
        return ResponseEntity.ok(cursRepo.gasesteToate());
    }
}