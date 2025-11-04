package ro.scoalasoferi.scoala_soferi_api.entities;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

@Entity
@Table(name = "Cursuri")
public class Curs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Curs")
    private Long idCurs;

    @Column(name = "Denumire")
    private String denumire;


    @Column(name = "Pret")
    private BigDecimal pret;


    @Column(name = "Ore")
    private Integer ore;


    @ManyToOne
    @JoinColumn(name = "ID_Categorie")
    private CategoriePermis categoriePermis;


    public Curs() {
    }


    public Long getIdCurs() {
        return idCurs;
    }

    public void setIdCurs(Long idCurs) {
        this.idCurs = idCurs;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public BigDecimal getPret() {
        return pret;
    }

    public void setPret(BigDecimal pret) {
        this.pret = pret;
    }

    public Integer getOre() {
        return ore;
    }

    public void setOre(Integer ore) {
        this.ore = ore;
    }

    public CategoriePermis getCategoriePermis() {
        return categoriePermis;
    }

    public void setCategoriePermis(CategoriePermis categoriePermis) {
        this.categoriePermis = categoriePermis;
    }
}