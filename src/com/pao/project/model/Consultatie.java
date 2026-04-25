package com.pao.project.model;

import java.time.LocalDateTime;

public class Consultatie {
    private String id;
    private Programare programare;
    private RaportMedical raportMedical;
    private boolean finalizata;

    public Consultatie(String id, Programare programare) {
        this.id = id;
        this.programare = programare;
        this.finalizata = false;
    }

    public void finalizeaza(String diagnostic, String recomandari) {
        this.raportMedical = new RaportMedical(
                "R-" + id,
                programare.getPacient().getId(),
                programare.getMedic().getId(),
                diagnostic,
                recomandari,
                LocalDateTime.now()
        );
        this.finalizata = true;
    }

    @Override
    public String toString() {
        return "Consultatie{" +
                "id='" + id + '\'' +
                ", programare=" + programare +
                ", finalizata=" + finalizata +
                ", raportMedical=" + raportMedical +
                '}';
    }

    public Programare getProgramare() {
        return programare;
    }

}
