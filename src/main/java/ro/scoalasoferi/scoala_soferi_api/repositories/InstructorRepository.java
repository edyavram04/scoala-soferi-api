package ro.scoalasoferi.scoala_soferi_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ro.scoalasoferi.scoala_soferi_api.entities.Instructor;

import java.util.List;
import java.util.Optional;

// (1) AICI AM SCHIMBAT DIN Long ÎN Integer
public interface InstructorRepository extends JpaRepository<Instructor, Integer> {

    // --- READ ---
    // (2) Parametrul este acum Integer
    @Query(value = "SELECT * FROM Instructori WHERE ID = :id", nativeQuery = true)
    Optional<Instructor> gasesteDupaId(@Param("id") Integer id);

    @Query(value = "SELECT * FROM Instructori", nativeQuery = true)
    List<Instructor> gasesteToti();

    // --- CREATE ---
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO Instructori (Nume, Prenume, CNP, Telefon) VALUES (:nume, :prenume, :cnp, :tel)", nativeQuery = true)
    void adaugaInstructorNou(
            @Param("nume") String nume,
            @Param("prenume") String prenume,
            @Param("cnp") String cnp,
            @Param("tel") String telefon
    );

    // --- UPDATE ---
    @Modifying
    @Transactional
    // (3) Parametrul id este Integer
    @Query(value = "UPDATE Instructori SET Nume = :nume, Prenume = :prenume, CNP = :cnp, Telefon = :tel WHERE ID = :id", nativeQuery = true)
    void actualizeazaInstructor(
            @Param("id") Integer id,
            @Param("nume") String nume,
            @Param("prenume") String prenume,
            @Param("cnp") String cnp,
            @Param("tel") String telefon
    );

    // --- DELETE ---
    @Modifying
    @Transactional
    // (4) Parametrul id este Integer
    @Query(value = "DELETE FROM Instructori WHERE ID = :id", nativeQuery = true)
    void stergeDupaId(@Param("id") Integer id);
}