package ro.scoalasoferi.scoala_soferi_api.entities;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
// Importuri pentru Validare
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "Elevi")
public class Elev {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "Nume")
    @NotBlank(message = "Numele este obligatoriu")
    @Pattern(regexp = "^[a-zA-ZăâîșțĂÂÎȘȚ\\s-]+$", message = "Numele poate conține doar litere")
    private String nume;

    @Column(name = "Prenume")
    @NotBlank(message = "Prenumele este obligatoriu")
    @Pattern(regexp = "^[a-zA-ZăâîșțĂÂÎȘȚ\\s-]+$", message = "Prenumele poate conține doar litere")
    private String prenume;

    @Column(name = "Telefon")
    @NotBlank(message = "Telefonul este obligatoriu")
    @Size(min = 10, message = "Telefonul trebuie să aibă minim 10 cifre")
    @Pattern(regexp = "\\d+", message = "Telefonul trebuie să conțină doar cifre")
    private String telefon;

    @ManyToOne
    @JoinColumn(name = "ID_Instructor")
    @JsonIgnoreProperties("elevi")
    @NotNull(message = "Trebuie să selectezi un instructor")
    private Instructor instructor;

    public Elev() {
    }

    // --- Getteri și Setteri ---

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }
}