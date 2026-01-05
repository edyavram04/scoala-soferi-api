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

public interface ElevCursRepository extends JpaRepository<ElevCurs, ElevCursId> {

    @Query(value = "SELECT * FROM Elevi_Cursuri", nativeQuery = true)
    List<ElevCurs> gasesteToate();

    // INSERT COMPLEX
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO Elevi_Cursuri (ID_Elev, ID_Curs, ID_Instructor, NrInmatriculare, DataStart, StarePlata) " +
            "VALUES (:idElev, :idCurs, :idInstr, :nrMasina, :data, :stare)",
            nativeQuery = true)
    void adaugaInscriereCompleta(
            @Param("idElev") Integer idElev,
            @Param("idCurs") Integer idCurs,
            @Param("idInstr") Integer idInstructor,
            @Param("nrMasina") String nrMasina,
            @Param("data") LocalDate dataStart,
            @Param("stare") String starePlata
    );

    // DELETE SPECIFIC (O singură înscriere)
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Elevi_Cursuri WHERE ID_Elev = :idElev AND ID_Curs = :idCurs", nativeQuery = true)
    void sterge(@Param("idElev") Integer idElev, @Param("idCurs") Integer idCurs);

    // --- METODA CARE ÎȚI LIPSEA (Pentru ElevController) ---
    // Șterge TOATE cursurile unui elev (când ștergi elevul de tot)
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Elevi_Cursuri WHERE ID_Elev = :idElev", nativeQuery = true)
    void stergeDupaIdElev(@Param("idElev") Integer idElev);
}