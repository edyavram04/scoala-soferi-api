/** Interfata cu metode care gestionează înscrierile.

 * @author Avram Eduard-Andrei
 * @version 11 Ianuarie 2026
 */
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

    // DELETE SPECIFIC
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Elevi_Cursuri WHERE ID_Elev = :idElev AND ID_Curs = :idCurs", nativeQuery = true)
    void sterge(@Param("idElev") Integer idElev, @Param("idCurs") Integer idCurs);


    // Șterge TOATE cursurile unui elev (cand sterg elevul de tot)
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Elevi_Cursuri WHERE ID_Elev = :idElev", nativeQuery = true)
    void stergeDupaIdElev(@Param("idElev") Integer idElev);



    // 1. Număr elevi per Instructor
    @Query(value = "SELECT i.Nume + ' ' + i.Prenume, COUNT(ec.ID_Elev) " +
            "FROM Elevi_Cursuri ec " +
            "JOIN Instructori i ON ec.ID_Instructor = i.ID " +
            "GROUP BY i.Nume, i.Prenume", nativeQuery = true)
    List<Object[]> getStatisticiInstructori();

    // 2. Bani produși per Tip Curs (Preț * Nr Înscrieri)
    @Query(value = "SELECT c.Denumire, COUNT(ec.ID_Elev) * c.Pret " +
            "FROM Elevi_Cursuri ec " +
            "JOIN Cursuri c ON ec.ID_Curs = c.ID_Curs " +
            "GROUP BY c.Denumire, c.Pret", nativeQuery = true)
    List<Object[]> getStatisticiBani();

    // 3. Cele mai folosite mașini
    @Query(value = "SELECT m.Marca + ' (' + m.NrInmatriculare + ')', COUNT(ec.ID_Elev) " +
            "FROM Elevi_Cursuri ec " +
            "JOIN Masini m ON ec.NrInmatriculare = m.NrInmatriculare " +
            "GROUP BY m.Marca, m.NrInmatriculare", nativeQuery = true)
    List<Object[]> getStatisticiMasini();

    @Query(value =
            "SELECT e.Nume + ' ' + e.Prenume, i.Nume + ' ' + i.Prenume " +
                    "FROM Elevi e " +
                    "JOIN Instructori i ON e.ID_Instructor = i.ID",
            nativeQuery = true)
    List<Object[]> getRaportElevInstructor();

    // 6. Lista completă: Elev - Mașină
    @Query(value =
            "SELECT e.Nume + ' ' + e.Prenume, m.Marca + ' ' + m.NrInmatriculare " +
                    "FROM Elevi_Cursuri ec " +
                    "JOIN Elevi e ON ec.ID_Elev = e.ID " +
                    "JOIN Masini m ON ec.NrInmatriculare = m.NrInmatriculare",
            nativeQuery = true)
    List<Object[]> getRaportElevMasina();

    // 7. Lista completă: Elev - Curs
    @Query(value =
            "SELECT e.Nume + ' ' + e.Prenume, c.Denumire " +
                    "FROM Elevi_Cursuri ec " +
                    "JOIN Elevi e ON ec.ID_Elev = e.ID " +
                    "JOIN Cursuri c ON ec.ID_Curs = c.ID_Curs",
            nativeQuery = true)
    List<Object[]> getRaportElevCurs();



    // 1. Elevii care s-au înscris la cursuri cu preț peste medie
    @Query(value =
            "SELECT e.Nume + ' ' + e.Prenume AS Elev, c.Denumire AS Curs, c.Pret " +
                    "FROM Elevi_Cursuri ec " +
                    "JOIN Elevi e ON ec.ID_Elev = e.ID " +
                    "JOIN Cursuri c ON ec.ID_Curs = c.ID_Curs " +
                    "WHERE c.Pret > (SELECT AVG(Pret) FROM Cursuri)",
            nativeQuery = true)
    List<Object[]> getEleviPremium();

    // 2. Mașinile care NU sunt folosite deloc (NOT IN)
    @Query(value =
            "SELECT m.Marca, m.NrInmatriculare " +
                    "FROM Masini m " +
                    "WHERE m.NrInmatriculare NOT IN " +
                    "(SELECT DISTINCT NrInmatriculare FROM Elevi_Cursuri WHERE NrInmatriculare IS NOT NULL)",
            nativeQuery = true)
    List<Object[]> getMasiniNeutilizate();

    // 3. Instructorii care au mai mult de 2 elevi activi
    @Query(value =
            "SELECT i.Nume + ' ' + i.Prenume, i.Telefon " +
                    "FROM Instructori i " +
                    "WHERE i.ID IN " +
                    "(SELECT ID_Instructor FROM Elevi_Cursuri GROUP BY ID_Instructor HAVING COUNT(ID_Elev) > 2)",
            nativeQuery = true)
    List<Object[]> getInstructoriAglomerati();

    // 4. Cursul cu cele mai multe înscrieri (MAX)
    @Query(value =
            "SELECT c.Denumire, COUNT(ec.ID_Elev) as NrInscrieri " +
                    "FROM Cursuri c " +
                    "JOIN Elevi_Cursuri ec ON c.ID_Curs = ec.ID_Curs " +
                    "GROUP BY c.Denumire " +
                    "HAVING COUNT(ec.ID_Elev) = " +
                    "(SELECT MAX(Cnt) FROM (SELECT COUNT(ID_Elev) as Cnt FROM Elevi_Cursuri GROUP BY ID_Curs) as T)",
            nativeQuery = true)
    List<Object[]> getTopCurs();
}