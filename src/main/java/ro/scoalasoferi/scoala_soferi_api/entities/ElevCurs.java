package ro.scoalasoferi.scoala_soferi_api.entities;

import java.time.LocalDate;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

@Entity
@Table(name = "Elevi_Cursuri")
public class ElevCurs {

    // (1) @EmbeddedId: Folosim cheia compusă pe care am creat-o mai devreme
    @EmbeddedId
    private ElevCursId id;

    // (2) Mapăm relația către Elev
    @ManyToOne
    // (3) @MapsId: Îi spune lui JPA "Câmpul 'idElev' din cheia @EmbeddedId
    //     este de fapt gestionat de această relație @ManyToOne"
    @MapsId("idElev")
    @JoinColumn(name = "ID_Elev")
    private Elev elev;

    // (4) Mapăm relația către Curs
    @ManyToOne
    @MapsId("idCurs") // La fel și aici, pentru 'idCurs'
    @JoinColumn(name = "ID_Curs")
    private Curs curs;

    // (5) Acum adăugăm coloanele suplimentare
    @Column(name = "DataStart")
    private LocalDate dataStart; // SQL DATE se mapează la LocalDate

    @Column(name = "StarePlata")
    private String starePlata;

    // --- Constructor gol (obligatoriu) ---
    public ElevCurs() {
    }

    // --- Getteri și Setteri ---
    // (Generează-i pentru TOATE câmpurile: id, elev, curs, dataStart, starePlata)

    public ElevCursId getId() {
        return id;
    }

    public void setId(ElevCursId id) {
        this.id = id;
    }

    public Elev getElev() {
        return elev;
    }

    public void setElev(Elev elev) {
        this.elev = elev;
    }

    public Curs getCurs() {
        return curs;
    }

    public void setCurs(Curs curs) {
        this.curs = curs;
    }

    public LocalDate getDataStart() {
        return dataStart;
    }

    public void setDataStart(LocalDate dataStart) {
        this.dataStart = dataStart;
    }

    public String getStarePlata() {
        return starePlata;
    }

    public void setStarePlata(String starePlata) {
        this.starePlata = starePlata;
    }
}