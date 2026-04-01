package com.pao.laboratory06.exercise2;

import java.util.Scanner;

public class PFAColaborator extends PersoanaFizica {
    private double cheltuieliLunare;

    private static final double SAL_MIN_LUNAR = 4050;
    private static final double SAL_MIN_ANUAL = SAL_MIN_LUNAR * 12;

    @Override
    public void citeste(Scanner in) {
        nume = in.next();
        prenume = in.next();
        venitLunar = in.nextDouble();
        cheltuieliLunare = in.nextDouble();
    }

    @Override
    public double calculeazaVenitNetAnual() {

        double venitNet = (venitLunar - cheltuieliLunare) * 12;

        // Impozit 10%
        double impozit = 0.10 * venitNet;

        // CASS 10%
        double cass;
        if (venitNet < 6 * SAL_MIN_ANUAL) {
            cass = 0.10 * (6 * SAL_MIN_ANUAL);
        } else if (venitNet <= 72 * SAL_MIN_ANUAL) {
            cass = 0.10 * venitNet;
        } else {
            cass = 0.10 * (72 * SAL_MIN_ANUAL);
        }

        // CAS 25%
        double cas;
        if (venitNet < 12 * SAL_MIN_ANUAL) {
            cas = 0;
        } else if (venitNet <= 24 * SAL_MIN_ANUAL) {
            cas = 0.25 * (12 * SAL_MIN_ANUAL);
        } else {
            cas = 0.25 * (24 * SAL_MIN_ANUAL);
        }

        return venitNet - impozit - cass - cas;
    }

    @Override
    public void afiseaza() {
        System.out.printf("PFA: %s, venit net anual: %.2f lei%n",
                getNumeComplet(), calculeazaVenitNetAnual());
    }

    @Override
    public String tipContract() {
        return "PFA";
    }
}
