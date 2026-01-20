/** Clasa pentru angajații școlii
 * @author Avram Eduard-Andrei
 * @version 11 Ianuarie 2026
 */
package ro.scoalasoferi.scoala_soferi_api.entities;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "Instructori")
public class Instructor {

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

    @Column(name = "CNP")
    @NotBlank(message = "CNP-ul este obligatoriu")
    @Size(min = 13, max = 13, message = "CNP-ul trebuie să aibă exact 13 cifre")
    @Pattern(regexp = "\\d+", message = "CNP-ul trebuie să conțină doar cifre")
    private String cnp;

    @Column(name = "Telefon")
    @NotBlank(message = "Telefonul este obligatoriu")
    @Size(min = 10, message = "Telefonul trebuie să aibă minim 10 cifre")
    @Pattern(regexp = "\\d+", message = "Telefonul trebuie să conțină doar cifre")
    private String telefon;

    public Instructor() {
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

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }
}