package ro.scoalasoferi.scoala_soferi_api.entities;

public class StatisticaDTO {
    private String eticheta;
    private Number valoare;

    public StatisticaDTO(String eticheta, Number valoare) {
        this.eticheta = eticheta;
        this.valoare = valoare;
    }


    public String getEticheta() { return eticheta; }
    public Number getValoare() { return valoare; }
}