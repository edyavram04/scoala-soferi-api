/** Clasa care definește cheia compusă a înscrierii
 * @author Avram Eduard-Andrei
 * @version 11 Ianuarie 2026
 */
package ro.scoalasoferi.scoala_soferi_api.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;


@Embeddable

public class ElevCursId implements Serializable {

    @Column(name = "ID_Elev")
    private Integer idElev;

    @Column(name = "ID_Curs")
    private Integer idCurs;


    public ElevCursId() {
    }


    public ElevCursId(Integer idElev, Integer idCurs) {
        this.idElev = idElev;
        this.idCurs = idCurs;
    }




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