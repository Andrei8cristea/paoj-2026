package com.pao.laboratory07.exercise2;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        int n = Integer.parseInt(in.nextLine());
        List<ActiuneComanda> comenzi = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            String[] t = in.nextLine().split(" ");

            switch (t[0]) {
                case "STANDARD" -> comenzi.add(
                        new ComandaStandard(t[1], t[2], Double.parseDouble(t[3]))
                );
                case "PRECOMANDA" -> comenzi.add(
                        new Precomanda(t[1], t[2], Double.parseDouble(t[3]), t[4])
                );
                case "ABONAMENT" -> comenzi.add(
                        new ComandaAbonament(t[1], t[2], Double.parseDouble(t[3]), Integer.parseInt(t[4]))
                );
            }
        }

        // Detectăm partea testată (A/B/C)
        String testPart = System.getProperty("testPart");

        if ("A".equals(testPart)) {
            for (ActiuneComanda c : comenzi) {
                ((Comanda) c).procesare();
            }
            return;
        }

        if ("B".equals(testPart)) {
            for (ActiuneComanda c : comenzi) {
                if (c.esteSpeciala()) {
                    c.afiseaza();
                }
            }
            return;
        }

        // PARTEA C
        comenzi.sort((a, b) -> Double.compare(((Comanda)b).valoare, ((Comanda)a).valoare));

        for (ActiuneComanda c : comenzi) {
            c.afiseaza();
        }

        System.out.println();

        ActiuneComanda max = comenzi.get(0);
        System.out.print("Comanda cu valoarea maxima: ");
        max.afiseaza();

        System.out.println();

        System.out.println("Sume și număr comenzi pe tip:");

        Map<String, Double> sume = new LinkedHashMap<>();
        Map<String, Integer> nr = new LinkedHashMap<>();

        for (ActiuneComanda c : comenzi) {
            String tip = c.tipComanda();
            double val = ((Comanda)c).valoare;

            sume.put(tip, sume.getOrDefault(tip, 0.0) + val);
            nr.put(tip, nr.getOrDefault(tip, 0) + 1);
        }

        for (String tip : List.of("STANDARD", "PRECOMANDA", "ABONAMENT")) {
            System.out.printf("%s: suma = %.2f lei, număr = %d%n",
                    tip, sume.getOrDefault(tip, 0.0), nr.getOrDefault(tip, 0));
        }
    }
}
