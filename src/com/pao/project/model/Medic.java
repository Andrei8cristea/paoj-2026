package com.pao.project.model;

import java.util.Objects;

public class Medic extends Persoana implements Comparable<Medic> {

    private Specializare specializare;

    public Medic(String id, String nume, String prenume, Specializare specializare) {
        super(id, nume, prenume);
        this.specializare = specializare;
    }

    public Specializare getSpecializare() { return specializare; }
    public void setSpecializare(Specializare specializare) { this.specializare = specializare; }

    @Override
    public String getRol() { return "Medic"; }

    @Override
    public int compareTo(Medic o) {
        int cmp = this.nume.compareToIgnoreCase(o.nume);
        if (cmp != 0) return cmp;
        return this.prenume.compareToIgnoreCase(o.prenume);
    }

    @Override
    public String toString() {
        return "Medic{" +
                "id='" + id + '\'' +
                ", nume='" + nume + '\'' +
                ", prenume='" + prenume + '\'' +
                ", specializare=" + specializare +
                '}';
    }
}
