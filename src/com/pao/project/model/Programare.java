package com.pao.project.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Programare {
    private String id;
    private Pacient pacient;
    private Medic medic;
    private LocalDateTime dataOra;
    private boolean anulata;

    public Programare(String id, Pacient pacient, Medic medic, LocalDateTime dataOra) {
        this.id = id;
        this.pacient = pacient;
        this.medic = medic;
        this.dataOra = dataOra;
        this.anulata = false;
    }

    public String getId() {
        return id;
    }

    public Pacient getPacient() {
        return pacient;
    }

    public Medic getMedic() {
        return medic;
    }

    public LocalDateTime getDataOra() {
        return dataOra;
    }

    public boolean isAnulata() {
        return anulata;
    }

    public void anuleaza() {
        this.anulata = true;
    }

    @Override
    public String toString() {
        return "Programare{" +
                "id='" + id + '\'' +
                ", pacient=" + pacient.getNume() + " " + pacient.getPrenume() +
                ", medic=" + medic.getNume() + " " + medic.getPrenume() +
                ", dataOra=" + dataOra +
                ", anulata=" + anulata +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Programare)) return false;
        Programare that = (Programare) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
