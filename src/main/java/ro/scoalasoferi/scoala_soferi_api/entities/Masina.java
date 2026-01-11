package ro.scoalasoferi.scoala_soferi_api.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties; // <--- IMPORT NOU
import jakarta.persistence.*;

@Entity
@Table(name = "Masini")
public class Masina {

    @Id
    @Column(name = "NrInmatriculare")
    private String nrInmatriculare;

    @Column(name = "Marca")
    private String marca;

    @Column(name = "Model")
    private String model;

    @ManyToOne
    @JoinColumn(name = "ID_Categorie")
    @JsonIgnoreProperties("masini")
    private CategoriePermis categoriePermis;

    public Masina() {}


    public String getNrInmatriculare() { return nrInmatriculare; }
    public void setNrInmatriculare(String nrInmatriculare) { this.nrInmatriculare = nrInmatriculare; }
    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public CategoriePermis getCategoriePermis() { return categoriePermis; }
    public void setCategoriePermis(CategoriePermis categoriePermis) { this.categoriePermis = categoriePermis; }
}