package ro.scoalasoferi.scoala_soferi_api.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

@Entity
@Table(name = "CategoriePermis")
public class CategoriePermis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Categorie")
    // Folosim Long pentru că e mai flexibil decât Integer și se potrivește cu INT din SQL
    private Integer idCategorie;

    @Column(name = "NumeCategorie")
    private String numeCategorie;

    // --- Constructor gol (obligatoriu) ---
    public CategoriePermis() {
    }

    // --- Getteri și Setteri ---
    // (Click dreapta -> Generate... -> Getter and Setter)


    public Integer getIdCategorie() {
        return idCategorie;
    }

    public void setIdCategorie(Integer idCategorie) {
        this.idCategorie = idCategorie;
    }

    public String getNumeCategorie() {
        return numeCategorie;
    }

    public void setNumeCategorie(String numeCategorie) {
        this.numeCategorie = numeCategorie;
    }
}
