package com.pao.laboratory07.exercise3;

public final class ComandaGratuita extends Comanda {

    public ComandaGratuita(String nume, String client) {
        super(nume, 0.0, client);
    }

    @Override
    public double pretFinal() {
        return 0.0;
    }

    @Override
    public void afiseaza() {
        System.out.printf("GIFT: %s, gratuit [PLACED] - client: %s%n",
                nume, client);
    }

    @Override
    public String tip() {
        return "GIFT";
    }
}
