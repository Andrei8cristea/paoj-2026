package com.pao.laboratory07.exercise3;

public sealed abstract class Comanda
        permits ComandaStandard, ComandaRedusa, ComandaGratuita {

    protected String nume;
    protected double pret;
    protected String client;

    public Comanda(String nume, double pret, String client) {
        this.nume = nume;
        this.pret = pret;
        this.client = client;
    }

    public abstract double pretFinal();
    public abstract void afiseaza();
    public abstract String tip();
}
