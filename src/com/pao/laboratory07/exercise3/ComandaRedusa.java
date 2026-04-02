package com.pao.laboratory07.exercise3;

public final class ComandaRedusa extends Comanda {

    private int discount;

    public ComandaRedusa(String nume, double pret, int discount, String client) {
        super(nume, pret, client);
        this.discount = discount;
    }

    @Override
    public double pretFinal() {
        return pret * (100 - discount) / 100.0;
    }

    public int getDiscount() {
        return discount;
    }

    @Override
    public void afiseaza() {
        System.out.printf("DISCOUNTED: %s, pret: %.2f lei (-%d%%) [PLACED] - client: %s%n",
                nume, pretFinal(), discount, client);
    }

    @Override
    public String tip() {
        return "DISCOUNTED";
    }
}
