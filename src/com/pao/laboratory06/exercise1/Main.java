package com.pao.laboratory06.exercise1;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Vezi Readme.md pentru cerințe

        Scanner scanner = new Scanner(System.in);
        String optiune = scanner.next();
        int numarAngajati = scanner.nextInt();
        Angajat[] angajati = new Angajat[numarAngajati];
        for (int i = 0; i < numarAngajati; i++) {
            angajati[i] = Angajat.citeste(scanner);
        }

        // cerinte: sorteaza in functie de optiune
        Comparator<Angajat> comparator = switch (optiune) {
            case "by_name" -> Comparator.comparing(Angajat::getNume);
            case "by_salary" -> null;
            case "by_salary_desc" -> (a1, a2) -> {
                int cmp = Double.compare(a2.getSalariu(), a1.getSalariu());
                return cmp;
            };
            default -> null;
        };

        if (comparator == null){
            Arrays.sort(angajati);
        }
        else{
            Arrays.sort(angajati, comparator);
        }
        for (Angajat angajat : angajati) {
            System.out.println(angajat);
        }
    }
}
