package com.pao.laboratory06.exercise2;

public abstract class Colaborator implements IOperatiiCitireScriere {
    protected String nume;
    protected String prenume;
    protected double venitLunar;

    public Colaborator() {}

    public abstract double calculeazaVenitNetAnual();

    public String getNumeComplet(){
        return nume + " " + prenume;
    }
}
