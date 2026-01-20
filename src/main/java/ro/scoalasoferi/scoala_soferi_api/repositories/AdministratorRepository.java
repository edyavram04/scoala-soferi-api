/** Interfata cu metode pentru autentificare
 * @author Avram Eduard-Andrei
 * @version 11 Ianuarie 2026
 */
package ro.scoalasoferi.scoala_soferi_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ro.scoalasoferi.scoala_soferi_api.entities.Administrator;

import java.util.Optional;


public interface AdministratorRepository extends JpaRepository<Administrator, String> {

    @Query(value = "SELECT * FROM Administratori WHERE username = :user AND parola = :pass",
            nativeQuery = true)
    Optional<Administrator> findByUsernameAndPasswordNative(
            @Param("user") String username,
            @Param("pass") String parola
    );
}