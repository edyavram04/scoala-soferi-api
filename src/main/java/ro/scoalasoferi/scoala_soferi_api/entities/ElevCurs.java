package ro.scoalasoferi.scoala_soferi_api.entities;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Elevi_Cursuri")
public class ElevCurs {

    @EmbeddedId
    private ElevCursId id;

    // --- RELAȚII (JOIN-uri) ---
    @ManyToOne
    @MapsId("idElev")
    @JoinColumn(name = "ID_Elev")
    private Elev elev;

    @ManyToOne
    @MapsId("idCurs")
    @JoinColumn(name = "ID_Curs")
    private Curs curs;

    @ManyToOne
    @JoinColumn(name = "ID_Instructor")
    private Instructor instructor;

    @ManyToOne
    @JoinColumn(name = "NrInmatriculare")
    private Masina masina;
    // --------------------------

    @Column(name = "DataStart")
    private LocalDate dataStart;

    @Column(name = "StarePlata")
    private String starePlata;

    public ElevCurs() {}

    // Getters și Setters
    public ElevCursId getId() { return id; }
    public void setId(ElevCursId id) { this.id = id; }

    public Elev getElev() { return elev; }
    public void setElev(Elev elev) { this.elev = elev; }

    public Curs getCurs() { return curs; }
    public void setCurs(Curs curs) { this.curs = curs; }

    public Instructor getInstructor() { return instructor; }
    public void setInstructor(Instructor instructor) { this.instructor = instructor; }

    public Masina getMasina() { return masina; }
    public void setMasina(Masina masina) { this.masina = masina; }

    public LocalDate getDataStart() { return dataStart; }
    public void setDataStart(LocalDate dataStart) { this.dataStart = dataStart; }

    public String getStarePlata() { return starePlata; }
    public void setStarePlata(String starePlata) { this.starePlata = starePlata; }
}