package ro.scoalasoferi.scoala_soferi_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ro.scoalasoferi.scoala_soferi_api.entities.ElevCurs;
import ro.scoalasoferi.scoala_soferi_api.entities.ElevCursId;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ElevCursRepository extends JpaRepository<ElevCurs, ElevCursId> {

    // --- READ ---
    // Aici ID-ul este compus din două părți
    @Query(value = "SELECT * FROM Elevi_Cursuri WHERE ID_Elev = :idElev AND ID_Curs = :idCurs",
            nativeQuery = true)
    Optional<ElevCurs> gasesteDupaId(
            @Param("idElev") Long idElev,
            @Param("idCurs") Long idCurs
    );

    @Query(value = "SELECT * FROM Elevi_Cursuri",
            nativeQuery = true)
    List<ElevCurs> gasesteToate();

    // --- CREATE ---
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO Elevi_Cursuri (ID_Elev, ID_Curs, DataStart, StarePlata) " +
            "VALUES (:idElev, :idCurs, :data, :stare)",
            nativeQuery = true)
    void adaugaInscriereNoua(
            @Param("idElev") Long idElev,
            @Param("idCurs") Long idCurs,
            @Param("data") LocalDate dataStart,
            @Param("stare") String starePlata
    );

    // --- UPDATE ---
    @Modifying
    @Transactional
    @Query(value = "UPDATE Elevi_Cursuri SET DataStart = :data, StarePlata = :stare " +
            "WHERE ID_Elev = :idElev AND ID_Curs = :idCurs",
            nativeQuery = true)
    void actualizeazaInscriere(
            @Param("idElev") Long idElev,
            @Param("idCurs") Long idCurs,
            @Param("data") LocalDate dataStart,
            @Param("stare") String starePlata
    );

    // --- DELETE ---
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Elevi_Cursuri WHERE ID_Elev = :idElev AND ID_Curs = :idCurs",
            nativeQuery = true)
    void stergeDupaId(
            @Param("idElev") Long idElev,
            @Param("idCurs") Long idCurs
    );


@Modifying
@Transactional
@Query(value = "DELETE FROM Elevi_Cursuri WHERE ID_Elev = :idElev",
        nativeQuery = true)
void stergeDupaIdElev(@Param("idElev") Integer idElev);
}