package com.pao.project.model;

public class Pacient extends Persoana {
    private String cnp;
    private String telefon;

    public Pacient(String id, String nume, String prenume, String cnp, String telefon) {
        super(id, nume, prenume);
        this.cnp = cnp;
        this.telefon = telefon;
    }

    public String getCnp() { return cnp; }
    public String getTelefon() { return telefon; }
    public void setTelefon(String telefon) { this.telefon = telefon; }

    @Override
    public String getRol() { return "Pacient"; }

    @Override
    public String toString() {
        return "Pacient{" +
                "id='" + id + '\'' +
                ", nume='" + nume + '\'' +
                ", prenume='" + prenume + '\'' +
                ", cnp='" + cnp + '\'' +
                ", telefon='" + telefon + '\'' +
                '}';
    }
}
