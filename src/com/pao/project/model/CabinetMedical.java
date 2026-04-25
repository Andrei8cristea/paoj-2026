package com.pao.project.model;

import java.util.ArrayList;
import java.util.List;

public class CabinetMedical {
    private String nume;
    private String adresa;
    private String telefon;
    private List<Medic> medici;

    public CabinetMedical(String nume, String adresa, String telefon) {
        this.nume = nume;
        this.adresa = adresa;
        this.telefon = telefon;
        this.medici = new ArrayList<>();
    }

    public String getNume() { return nume; }
    public String getAdresa() { return adresa; }
    public String getTelefon() { return telefon; }
    public List<Medic> getMedici() { return medici; }

    public void adaugaMedic(Medic medic) {
        if (medic != null && !medici.contains(medic)) {
            medici.add(medic);
        }
    }

    public void eliminaMedic(Medic medic) {
        medici.remove(medic);
    }

    public int numarMedici() {
        return medici.size();
    }

    @Override
    public String toString() {
        return "CabinetMedical{" +
                "nume='" + nume + '\'' +
                ", adresa='" + adresa + '\'' +
                ", telefon='" + telefon + '\'' +
                ", numarMedici=" + medici.size() +
                '}';
    }
}
