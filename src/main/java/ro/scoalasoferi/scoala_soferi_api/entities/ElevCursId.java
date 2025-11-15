package ro.scoalasoferi.scoala_soferi_api.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

// (1) @Embeddable: Îi spune lui JPA că această clasă va fi
//     "încorporată" ca parte a altei entități (ca o cheie)
@Embeddable
// (2) Serializable: Este o cerință tehnică pentru cheile compuse
public class ElevCursId implements Serializable {

    @Column(name = "ID_Elev")
    private Integer idElev;

    @Column(name = "ID_Curs")
    private Integer idCurs;

    // --- Constructor gol (obligatoriu) ---
    public ElevCursId() {
    }

    // --- Constructor cu parametri (util) ---
    public ElevCursId(Integer idElev, Integer idCurs) {
        this.idElev = idElev;
        this.idCurs = idCurs;
    }

    // --- Getteri și Setteri (obligatorii) ---
    // (Generează-i automat)


    public Integer getIdCurs() {
        return idCurs;
    }

    public void setIdCurs(Integer idCurs) {
        this.idCurs = idCurs;
    }

    public Integer getIdElev() {
        return idElev;
    }

    public void setIdElev(Integer idElev) {
        this.idElev = idElev;
    }

    // --- equals() și hashCode() (ABSOLUT OBLIGATORII) ---
    // JPA are nevoie de ele pentru a compara cheile.
    // (Click dreapta -> Generate... -> equals() and hashCode() -> Selectează ambele câmpuri)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ElevCursId that = (ElevCursId) o;
        return Objects.equals(idElev, that.idElev) && Objects.equals(idCurs, that.idCurs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idElev, idCurs);
    }
}