package com.pao.laboratory07.exercise3;

public final class ComandaStandard extends Comanda {

    public ComandaStandard(String nume, double pret, String client) {
        super(nume, pret, client);
    }

    @Override
    public double pretFinal() {
        return pret;
    }

    @Override
    public void afiseaza() {
        System.out.printf("STANDARD: %s, pret: %.2f lei [PLACED] - client: %s%n",
                nume, pretFinal(), client);
    }

    @Override
    public String tip() {
        return "STANDARD";
    }
}
