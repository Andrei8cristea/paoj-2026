package com.pao.laboratory06.exercise2;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        int n = in.nextInt();
        List<Colaborator> lista = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            String tip = in.next();

            Colaborator c = switch (tip) {
                case "CIM" -> new CIMColaborator();
                case "PFA" -> new PFAColaborator();
                case "SRL" -> new SRLColaborator();
                default -> throw new RuntimeException("Tip invalid");
            };

            c.citeste(in);
            lista.add(c);
        }

        // 1. Afișare în ordinea inputului
        lista.forEach(Colaborator::afiseaza);

        // 2. Maxim
        Colaborator max = lista.stream()
                .max(Comparator.comparingDouble(Colaborator::calculeazaVenitNetAnual))
                .orElse(null);

        System.out.println();
        System.out.print("Colaborator cu venit net maxim: ");
        max.afiseaza();

        // 3. Persoane juridice
        System.out.println();
        System.out.println("Colaboratori persoane juridice:");
        lista.stream()
                .filter(c -> c instanceof PersoanaJuridica)
                .forEach(Colaborator::afiseaza);

        // 4. Summary
        System.out.println();
        System.out.println("Sume și număr colaboratori pe tip:");

        String[] tipuri = {"CIM", "PFA", "SRL"};

        for (String tip : tipuri) {
            long count = lista.stream()
                    .filter(c -> c.tipContract().equals(tip))
                    .count();

            if (count == 0) {
                System.out.printf("%s: suma = nu lei, număr = null%n", tip);
                continue;
            }

            double suma = lista.stream()
                    .filter(c -> c.tipContract().equals(tip))
                    .mapToDouble(Colaborator::calculeazaVenitNetAnual)
                    .sum();

            System.out.printf("%s: suma = %.2f lei, număr = %d%n",
                    tip, suma, count);
        }

    }
}
