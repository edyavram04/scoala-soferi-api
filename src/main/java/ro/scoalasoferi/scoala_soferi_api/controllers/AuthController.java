package ro.scoalasoferi.scoala_soferi_api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*; // Asigură-te că importurile sunt corecte
import ro.scoalasoferi.scoala_soferi_api.entities.Administrator;
import ro.scoalasoferi.scoala_soferi_api.repositories.AdministratorRepository;

import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/api/auth")

public class AuthController {

    @Autowired
    private AdministratorRepository adminRepo;

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