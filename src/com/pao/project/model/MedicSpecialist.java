package com.pao.project.model;

public class MedicSpecialist extends Medic {
    private String competenta;   // ex: "Chirurgie minim invazivă", "Ecocardiografie"
    private int aniExperienta;

    public MedicSpecialist(String id, String nume, String prenume,
                           Specializare specializare, String competenta, int aniExperienta) {
        super(id, nume, prenume, specializare);
        this.competenta = competenta;
        this.aniExperienta = aniExperienta;
    }

    public String getCompetenta() { return competenta; }
    public int getAniExperienta() { return aniExperienta; }

    @Override
    public String getRol() { return "Medic Specialist"; }

    @Override
    public String toString() {
        return "MedicSpecialist{" +
                "id='" + getId() + '\'' +
                ", nume='" + getNume() + '\'' +
                ", prenume='" + getPrenume() + '\'' +
                ", specializare=" + getSpecializare() +
                ", competenta='" + competenta + '\'' +
                ", aniExperienta=" + aniExperienta +
                '}';
    }
}
