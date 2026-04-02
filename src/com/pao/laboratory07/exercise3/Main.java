package com.pao.laboratory07.exercise3;

import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {

        //Hardcodez comenzile
        List<Comanda> comenzi = List.of(
                new ComandaStandard("Laptop", 2500.0, "Alice"),
                new ComandaRedusa("Headphones", 200.0, 20, "Bob"),
                new ComandaGratuita("Sticker", "Charlie"),
                new ComandaStandard("Mouse", 80.0, "Alice"),
                new ComandaRedusa("Keyboard", 300.0, 10, "Dave")
        );

        //Init
        System.out.println("--- COMENZI INITIALE ---");
        comenzi.forEach(Comanda::afiseaza);
        System.out.println();


        // ============================
        // STATS
        // ============================
        System.out.println("--- STATS ---");

        Map<String, Double> medii =
                comenzi.stream()
                        .collect(Collectors.groupingBy(
                                Comanda::tip,
                                Collectors.averagingDouble(Comanda::pretFinal)
                        ));

        System.out.printf("STANDARD: medie = %.2f lei%n",
                medii.getOrDefault("STANDARD", 0.0));
        System.out.printf("DISCOUNTED: medie = %.2f lei%n",
                medii.getOrDefault("DISCOUNTED", 0.0));
        System.out.printf("GIFT: medie = %.2f lei%n",
                medii.getOrDefault("GIFT", 0.0));

        System.out.println();


        // ============================
        // FILTER >= 100
        // ============================
        double threshold = 100;
        System.out.printf("--- FILTER (>= %.2f) ---%n", threshold);

        comenzi.stream()
                .filter(c -> c.pretFinal() >= threshold)
                .forEach(Main::afiseazaSimplu);

        System.out.println();


        // ============================
        // SORT by client, then by pret
        // ============================
        System.out.println("--- SORT (by client, then by pret) ---");

        comenzi.stream()
                .sorted(Comparator.comparing((Comanda c) -> c.client)
                        .thenComparing(Comanda::pretFinal))
                .forEach(Main::afiseazaSimplu);

        System.out.println();


        // ============================
        // SPECIAL (discount > 15%)
        // ============================
        System.out.println("--- SPECIAL (discount > 15%) ---");

        comenzi.stream()
                .filter(c -> c instanceof ComandaRedusa cr && cr.getDiscount() > 15)
                .forEach(Main::afiseazaSimplu);

        System.out.println("\n=== FINAL ===");
    }


    // Funcție de afișare simplificată pentru FILTER / SORT / SPECIAL
    private static void afiseazaSimplu(Comanda c) {
        if (c instanceof ComandaGratuita) {
            System.out.printf("GIFT: %s, gratuit - client: %s%n",
                    c.nume, c.client);
        } else if (c instanceof ComandaRedusa cr) {
            System.out.printf("DISCOUNTED: %s, pret: %.2f lei (-%d%%) - client: %s%n",
                    c.nume, c.pretFinal(), cr.getDiscount(), c.client);
        } else {
            System.out.printf("STANDARD: %s, pret: %.2f lei - client: %s%n",
                    c.nume, c.pretFinal(), c.client);
        }
    }
}
