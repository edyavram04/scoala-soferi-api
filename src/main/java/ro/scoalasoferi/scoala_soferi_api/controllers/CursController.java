package ro.scoalasoferi.scoala_soferi_api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.scoalasoferi.scoala_soferi_api.entities.Curs;
import ro.scoalasoferi.scoala_soferi_api.repositories.CursRepository;

import java.util.List;

@RestController
@RequestMapping("/api/cursuri") // <--- Asta definește adresa URL

public class CursController {

    @Autowired
    private CursRepository cursRepo;

    @GetMapping
    public ResponseEntity<List<Curs>> getAllCursuri() {
        return ResponseEntity.ok(cursRepo.gasesteToate());
    }
}