package ro.scoalasoferi.scoala_soferi_api.entities;

public class StatisticaDTO {
    private String eticheta; // Ex: Nume Instructor sau Nume Curs
    private Number valoare;  // Ex: 5 elevi sau 10000 RON

    public StatisticaDTO(String eticheta, Number valoare) {
        this.eticheta = eticheta;
        this.valoare = valoare;
    }

    // Getters
    public String getEticheta() { return eticheta; }
    public Number getValoare() { return valoare; }
}