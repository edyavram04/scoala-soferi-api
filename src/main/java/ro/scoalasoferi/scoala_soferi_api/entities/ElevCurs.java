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


    @EmbeddedId
    private ElevCursId id;


    @ManyToOne

    @MapsId("idElev")
    @JoinColumn(name = "ID_Elev")
    private Elev elev;


    @ManyToOne
    @MapsId("idCurs")
    @JoinColumn(name = "ID_Curs")
    private Curs curs;


    @Column(name = "DataStart")
    private LocalDate dataStart;

    @Column(name = "StarePlata")
    private String starePlata;


    public ElevCurs() {
    }

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