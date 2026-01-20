/** Interfata cu metode pentru gestionarea categoriilor de permis.

 * @author Avram Eduard-Andrei
 * @version 11 Ianuarie 2026
 */
package ro.scoalasoferi.scoala_soferi_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ro.scoalasoferi.scoala_soferi_api.entities.CategoriePermis;

import java.util.List;
import java.util.Optional;

public interface CategoriePermisRepository extends JpaRepository<CategoriePermis, Long> {

    //READ
    @Query(value = "SELECT * FROM CategoriePermis WHERE ID_Categorie = :id",
            nativeQuery = true)
    Optional<CategoriePermis> gasesteDupaId(@Param("id") Long id);

    @Query(value = "SELECT * FROM CategoriePermis",
            nativeQuery = true)
    List<CategoriePermis> gasesteToate();

    //  CREATE
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO CategoriePermis (NumeCategorie) VALUES (:nume)",
            nativeQuery = true)
    void adaugaCategorieNoua(@Param("nume") String numeCategorie);

    //UPDATE
    @Modifying
    @Transactional
    @Query(value = "UPDATE CategoriePermis SET NumeCategorie = :nume " +
            "WHERE ID_Categorie = :id",
            nativeQuery = true)
    void actualizeazaCategorie(
            @Param("id") Long id,
            @Param("nume") String numeCategorie
    );

    //DELETE
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM CategoriePermis WHERE ID_Categorie = :id",
            nativeQuery = true)
    void stergeDupaId(@Param("id") Long id);
}