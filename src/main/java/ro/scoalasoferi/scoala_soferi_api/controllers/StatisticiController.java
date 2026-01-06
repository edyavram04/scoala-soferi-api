package ro.scoalasoferi.scoala_soferi_api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.scoalasoferi.scoala_soferi_api.entities.StatisticaDTO;
import ro.scoalasoferi.scoala_soferi_api.repositories.ElevCursRepository;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/statistici")
// Nu mai punem @CrossOrigin pentru ca avem Config global, dar daca vrei siguranta pune-l
public class StatisticiController {

    @Autowired
    private ElevCursRepository repo;

    // Helper pentru conversie
    private List<StatisticaDTO> converteste(List<Object[]> listaBruta) {
        List<StatisticaDTO> rezultat = new ArrayList<>();
        for (Object[] rand : listaBruta) {
            String nume = (String) rand[0];
            Number valoare = (Number) rand[1];
            rezultat.add(new StatisticaDTO(nume, valoare));
        }
        return rezultat;
    }

    @GetMapping("/instructori")
    public ResponseEntity<List<StatisticaDTO>> statInstructori() {
        return ResponseEntity.ok(converteste(repo.getStatisticiInstructori()));
    }

    @GetMapping("/bani")
    public ResponseEntity<List<StatisticaDTO>> statBani() {
        return ResponseEntity.ok(converteste(repo.getStatisticiBani()));
    }

    @GetMapping("/masini")
    public ResponseEntity<List<StatisticaDTO>> statMasini() {
        return ResponseEntity.ok(converteste(repo.getStatisticiMasini()));
    }

    @GetMapping("/raport-elev-instructor")
    public ResponseEntity<List<StatisticaDTO>> getRaportElevInstructor() {
        List<Object[]> date = repo.getRaportElevInstructor();
        List<StatisticaDTO> rezultat = new ArrayList<>();
        for (Object[] rand : date) {
            // Aici "valoare" e un String, dar DTO-ul nostru așteaptă Number.
            // Putem face un truc: punem 0 la valoare, și afișăm textul în etichetă combinată
            String elev = (String) rand[0];
            String instr = (String) rand[1];
            rezultat.add(new StatisticaDTO(elev + " -> " + instr, 0));
        }
        return ResponseEntity.ok(rezultat);
    }

    @GetMapping("/raport-elev-masina")
    public ResponseEntity<List<StatisticaDTO>> getRaportElevMasina() {
        List<Object[]> date = repo.getRaportElevMasina();
        List<StatisticaDTO> rezultat = new ArrayList<>();
        for (Object[] rand : date) {
            String elev = (String) rand[0];
            String masina = (String) rand[1];
            rezultat.add(new StatisticaDTO(elev + " -> " + masina, 0));
        }
        return ResponseEntity.ok(rezultat);
    }

    @GetMapping("/raport-elev-curs")
    public ResponseEntity<List<StatisticaDTO>> getRaportElevCurs() {
        List<Object[]> date = repo.getRaportElevCurs();
        List<StatisticaDTO> rezultat = new ArrayList<>();
        for (Object[] rand : date) {
            String elev = (String) rand[0];
            String curs = (String) rand[1];
            rezultat.add(new StatisticaDTO(elev + " [" + curs + "]", 0));
        }
        return ResponseEntity.ok(rezultat);
    }

    @GetMapping("/elevi-premium")
    public ResponseEntity<List<StatisticaDTO>> getEleviPremium() {
        List<Object[]> date = repo.getEleviPremium();
        List<StatisticaDTO> rezultat = new ArrayList<>();
        for (Object[] rand : date) {
            String numeElev = (String) rand[0];
            String numeCurs = (String) rand[1];
            // Combinăm Numele cu Cursul pentru etichetă
            rezultat.add(new StatisticaDTO(numeElev + " (" + numeCurs + ")", (Number) rand[2]));
        }
        return ResponseEntity.ok(rezultat);
    }

    @GetMapping("/masini-neutilizate")
    public ResponseEntity<List<StatisticaDTO>> getMasiniNeutilizate() {
        List<Object[]> date = repo.getMasiniNeutilizate();
        List<StatisticaDTO> rezultat = new ArrayList<>();
        for (Object[] rand : date) {
            String marca = (String) rand[0];
            String nr = (String) rand[1];
            // Punem valoarea 0 sau 1 simbolic, pentru că aici doar le listăm
            rezultat.add(new StatisticaDTO(marca + " - " + nr, 0));
        }
        return ResponseEntity.ok(rezultat);
    }

    @GetMapping("/instructori-top")
    public ResponseEntity<List<StatisticaDTO>> getInstructoriTop() {
        List<Object[]> date = repo.getInstructoriAglomerati();
        List<StatisticaDTO> rezultat = new ArrayList<>();
        for (Object[] rand : date) {
            String nume = (String) rand[0];
            // Nu avem un număr în select, punem 1 simbolic
            rezultat.add(new StatisticaDTO(nume, 1));
        }
        return ResponseEntity.ok(rezultat);
    }

    @GetMapping("/top-curs")
    public ResponseEntity<List<StatisticaDTO>> getTopCurs() {
        return ResponseEntity.ok(converteste(repo.getTopCurs()));
    }
}

