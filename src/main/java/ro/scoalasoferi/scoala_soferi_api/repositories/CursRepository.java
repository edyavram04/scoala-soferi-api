/** Interfata cu metode pentru gestionarea tipurilor de cursuri.

 * @author Avram Eduard-Andrei
 * @version 11 Ianuarie 2026
 */
package ro.scoalasoferi.scoala_soferi_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ro.scoalasoferi.scoala_soferi_api.entities.Curs;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface CursRepository extends JpaRepository<Curs, Long> {

    // READ
    @Query(value = "SELECT * FROM Cursuri WHERE ID_Curs = :id",
            nativeQuery = true)
    Optional<Curs> gasesteDupaId(@Param("id") Long id);

    @Query(value = "SELECT * FROM Cursuri",
            nativeQuery = true)
    List<Curs> gasesteToate();

    // CREATE
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO Cursuri (Denumire, Pret, Ore, ID_Categorie) " +
            "VALUES (:den, :pret, :ore, :idCat)",
            nativeQuery = true)
    void adaugaCursNou(
            @Param("den") String denumire,
            @Param("pret") BigDecimal pret,
            @Param("ore") Integer ore,
            @Param("idCat") Long idCategorie
    );

    // UPDATE
    @Modifying
    @Transactional
    @Query(value = "UPDATE Cursuri SET Denumire = :den, Pret = :pret, " +
            "Ore = :ore, ID_Categorie = :idCat " +
            "WHERE ID_Curs = :id",
            nativeQuery = true)
    void actualizeazaCurs(
            @Param("id") Long id,
            @Param("den") String denumire,
            @Param("pret") BigDecimal pret,
            @Param("ore") Integer ore,
            @Param("idCat") Long idCategorie
    );

    //DELETE
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Cursuri WHERE ID_Curs = :id",
            nativeQuery = true)
    void stergeDupaId(@Param("id") Long id);
}