package ro.scoalasoferi.scoala_soferi_api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.scoalasoferi.scoala_soferi_api.entities.*;
import ro.scoalasoferi.scoala_soferi_api.repositories.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/inscrieri")

public class InscriereController {

    @Autowired private ElevCursRepository repo;


    @Autowired private ElevRepository elevRepo;
    @Autowired private InstructorRepository instrRepo;

    // Pachetul de date primit de la React
    public static class InscriereRequest {
        public Integer idElev;
        public Integer idCurs;
        public Integer idInstructor;
        public String nrMasina;
        public LocalDate dataStart;
        public String starePlata;
    }

    @GetMapping
    public List<ElevCurs> getAll() {
        return repo.gasesteToate();
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody InscriereRequest req) {
        try {
            // 1. ACTUALIZARE AUTOMATĂ ELEV (SINCRONIZARE)
            Optional<Elev> elevOpt = elevRepo.findById(req.idElev);
            Optional<Instructor> instrOpt = instrRepo.findById(req.idInstructor);

            if (elevOpt.isPresent() && instrOpt.isPresent()) {
                Elev elev = elevOpt.get();
                Instructor instructorNou = instrOpt.get();


                if (elev.getInstructor() == null || !elev.getInstructor().getId().equals(instructorNou.getId())) {
                    elev.setInstructor(instructorNou);
                    elevRepo.save(elev);
                    System.out.println("Instructor actualizat pentru elevul ID: " + elev.getId());
                }
            }


            // SALVAREA ÎNSCRIERII
            repo.adaugaInscriereCompleta(
                    req.idElev,
                    req.idCurs,
                    req.idInstructor,
                    req.nrMasina,
                    req.dataStart,
                    req.starePlata
            );

            return ResponseEntity.ok("Înscriere salvată!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Eroare la salvare.");
        }
    }

    @DeleteMapping
    public ResponseEntity<?> delete(@RequestParam Integer idElev, @RequestParam Integer idCurs) {
        repo.sterge(idElev, idCurs);
        return ResponseEntity.ok("Șters!");
    }
}