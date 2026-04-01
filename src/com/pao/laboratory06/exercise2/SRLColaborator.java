package com.pao.laboratory06.exercise2;

import java.util.Scanner;

public class SRLColaborator extends PersoanaJuridica {
    private double cheltuieliLunare;

    @Override
    public void citeste(Scanner in) {
        nume = in.next();
        prenume = in.next();
        venitLunar = in.nextDouble();
        cheltuieliLunare = in.nextDouble();
    }

    @Override
    public double calculeazaVenitNetAnual() {
        double profit = (venitLunar - cheltuieliLunare) * 12;
        return profit * 0.84;
    }


    @Override
    public void afiseaza() {
        System.out.printf("SRL: %s, venit net anual: %.2f lei%n",
                getNumeComplet(), calculeazaVenitNetAnual());
    }

    @Override
    public String tipContract() {
        return "SRL";
    }
}
