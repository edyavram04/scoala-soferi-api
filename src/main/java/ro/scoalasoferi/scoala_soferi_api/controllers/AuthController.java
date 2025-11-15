package ro.scoalasoferi.scoala_soferi_api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*; // Asigură-te că importurile sunt corecte
import ro.scoalasoferi.scoala_soferi_api.entities.Administrator;
import ro.scoalasoferi.scoala_soferi_api.repositories.AdministratorRepository;

import java.util.Map;
import java.util.Optional;

// (1) VERIFICAREA #1: Este @RestController aici?
@RestController
// (2) VERIFICAREA #2: Ai @RequestMapping corect aici?
@RequestMapping("/api/auth")
// (3) VERIFICAREA #3: Numele clasei este 'AuthController'?
public class AuthController { // <-- Numele tău de clasă

    @Autowired
    private AdministratorRepository adminRepo;

    // (4) VERIFICAREA #4: Ai @PostMapping("/login") aici?
    // Adresa finală devine: /api/auth + /login
    @PostMapping("/login")
    public ResponseEntity<?> processLogin(@RequestBody Map<String, String> loginRequest) {

        String username = loginRequest.get("username");
        String password = loginRequest.get("password");

        Optional<Administrator> adminCutie = adminRepo.findByUsernameAndPasswordNative(username, password);

        if (adminCutie.isPresent()) {
            Map<String, String> response = Map.of("status", "success", "user", username);
            return ResponseEntity.ok(response);
        } else {
            Map<String, String> response = Map.of("status", "error", "message", "Invalid credentials");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }
}