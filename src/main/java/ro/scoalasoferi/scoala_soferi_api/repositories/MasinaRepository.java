/** Interfata cu metode pentru gestionarea flotei auto..
 * @author Avram Eduard-Andrei
 * @version 11 Ianuarie 2026
 */
package ro.scoalasoferi.scoala_soferi_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ro.scoalasoferi.scoala_soferi_api.entities.Masina;

import java.util.List;
import java.util.Optional;


public interface MasinaRepository extends JpaRepository<Masina, String> {

    //READ
    @Query(value = "SELECT * FROM Masini WHERE NrInmatriculare = :id",
            nativeQuery = true)
    Optional<Masina> gasesteDupaId(@Param("id") String id);

    @Query(value = "SELECT * FROM Masini",
            nativeQuery = true)
    List<Masina> gasesteToate();

    //CREATE
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO Masini (NrInmatriculare, Marca, Model, ID_Categorie) " +
            "VALUES (:nr, :marca, :model, :idCat)",
            nativeQuery = true)
    void adaugaMasinaNoua(
            @Param("nr") String nrInmatriculare,
            @Param("marca") String marca,
            @Param("model") String model,
            @Param("idCat") Long idCategorie
    );

    //UPDATE
    @Modifying
    @Transactional
    @Query(value = "UPDATE Masini SET Marca = :marca, Model = :model, " +
            "ID_Categorie = :idCat " +
            "WHERE NrInmatriculare = :nr",
            nativeQuery = true)
    void actualizeazaMasina(
            @Param("nr") String nrInmatriculare,
            @Param("marca") String marca,
            @Param("model") String model,
            @Param("idCat") Long idCategorie
    );

    // DELETE
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Masini WHERE NrInmatriculare = :id",
            nativeQuery = true)
    void stergeDupaId(@Param("id") String id);
}