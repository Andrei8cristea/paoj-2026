package com.pao.project.model;

import java.util.Objects;

public abstract class Persoana {
    protected String id;
    protected String nume;
    protected String prenume;

    public Persoana(String id, String nume, String prenume) {
        this.id = id;
        this.nume = nume;
        this.prenume = prenume;
    }

    public String getId() { return id; }
    public String getNume() { return nume; }
    public String getPrenume() { return prenume; }

    public abstract String getRol();

    @Override
    public String toString() {
        return getRol() + "{" +
                "id='" + id + '\'' +
                ", nume='" + nume + '\'' +
                ", prenume='" + prenume + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Persoana)) return false;
        Persoana persoana = (Persoana) o;
        return Objects.equals(id, persoana.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
