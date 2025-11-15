package ro.scoalasoferi.scoala_soferi_api.repositories;

// Importurile necesare
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ro.scoalasoferi.scoala_soferi_api.entities.Elev;

import java.util.List;
import java.util.Optional;

// (1) CORECȚIE AICI: Cheia primară a lui 'Elev' este Integer, nu Long
public interface ElevRepository extends JpaRepository<Elev, Integer> {

    @Query(value = "SELECT * FROM Elevi WHERE ID = :id",
            nativeQuery = true)
        // (2) CORECȚIE AICI: Parametrul 'id' este Integer
    Optional<Elev> gasesteDupaId(@Param("id") Integer id);

    @Query(value = "SELECT * FROM Elevi",
            nativeQuery = true)
    List<Elev> gasesteToti();

    // --- CREATE (INSERT) ---
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO Elevi (Nume, Prenume, Telefon, ID_Instructor) " +
            "VALUES (:nume, :prenume, :tel, :idInstr)",
            nativeQuery = true)
    void adaugaElevNou(
            @Param("nume") String nume,
            @Param("prenume") String prenume,
            @Param("tel") String telefon,
            // (3) CORECȚIE AICI: ID_Instructor este Integer
            @Param("idInstr") Integer idInstructor
    );

    // --- UPDATE ---
    @Modifying
    @Transactional
    @Query(value = "UPDATE Elevi SET Nume = :nume, Prenume = :prenume, " +
            "Telefon = :tel, ID_Instructor = :idInstr " +
            "WHERE ID = :id",
            nativeQuery = true)
    void actualizeazaElev(
            // (4) CORECȚIE AICI: ID-ul este Integer
            @Param("id") Integer id,
            @Param("nume") String nume,
            @Param("prenume") String prenume,
            @Param("tel") String telefon,
            // (5) CORECȚIE AICI: ID_Instructor este Integer
            @Param("idInstr") Integer idInstructor
    );

    // --- DELETE ---
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Elevi WHERE ID = :id",
            nativeQuery = true)
    // (6) CORECȚIE AICI: ID-ul este Integer
    void stergeDupaId(@Param("id") Integer id);
}