package com.pao.laboratory06.exercise2;

import java.util.Scanner;

public class CIMColaborator extends PersoanaFizica {
    private boolean bonus;

    @Override
    public void citeste(Scanner in) {
        nume = in.next();
        prenume = in.next();
        venitLunar = in.nextDouble();

        if (in.hasNext("DA") || in.hasNext("NU")) {
            bonus = in.next().equalsIgnoreCase("DA");
        } else {
            bonus = false;
        }
    }

    @Override
    public boolean areBonus() {
        return bonus;
    }

    @Override
    public double calculeazaVenitNetAnual() {
        double venit = venitLunar * 12 * 0.55;
        if (bonus) venit *= 1.10;
        return venit;
    }

    @Override
    public void afiseaza() {
        System.out.printf("CIM: %s, venit net anual: %.2f lei%n",
                getNumeComplet(), calculeazaVenitNetAnual());
    }

    @Override
    public String tipContract() {
        return "CIM";
    }
}
