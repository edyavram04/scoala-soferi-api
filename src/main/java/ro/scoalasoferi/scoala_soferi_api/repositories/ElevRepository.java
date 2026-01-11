package ro.scoalasoferi.scoala_soferi_api.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ro.scoalasoferi.scoala_soferi_api.entities.Elev;

import java.util.List;
import java.util.Optional;


public interface ElevRepository extends JpaRepository<Elev, Integer> {

    @Query(value = "SELECT * FROM Elevi WHERE ID = :id",
            nativeQuery = true)

    Optional<Elev> gasesteDupaId(@Param("id") Integer id);

    @Query(value = "SELECT * FROM Elevi",
            nativeQuery = true)
    List<Elev> gasesteToti();


    @Modifying
    @Transactional
    @Query(value = "INSERT INTO Elevi (Nume, Prenume, Telefon, ID_Instructor) " +
            "VALUES (:nume, :prenume, :tel, :idInstr)",
            nativeQuery = true)
    void adaugaElevNou(
            @Param("nume") String nume,
            @Param("prenume") String prenume,
            @Param("tel") String telefon,
            @Param("idInstr") Integer idInstructor
    );


    @Modifying
    @Transactional
    @Query(value = "UPDATE Elevi SET Nume = :nume, Prenume = :prenume, " +
            "Telefon = :tel, ID_Instructor = :idInstr " +
            "WHERE ID = :id",
            nativeQuery = true)
    void actualizeazaElev(
            @Param("id") Integer id,
            @Param("nume") String nume,
            @Param("prenume") String prenume,
            @Param("tel") String telefon,
            @Param("idInstr") Integer idInstructor
    );

    //DELETE
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Elevi WHERE ID = :id",
            nativeQuery = true)
    void stergeDupaId(@Param("id") Integer id);
}