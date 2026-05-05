package com.pao.laboratory10.exercise3;

import com.pao.laboratory10.exercise1.TipTranzactie;
import com.pao.laboratory10.exercise1.Tranzactie;

import java.util.*;
import java.util.stream.Collectors;

public class Main {

    // Extindem tranzacția cu un câmp contSursa pentru operația 5
    static class TranzactieExtinsa extends Tranzactie {
        private final String contSursa;

        public TranzactieExtinsa(int id, double suma, String data, TipTranzactie tip, String contSursa) {
            super(id, suma, data, tip);
            this.contSursa = contSursa;
        }

        public String getContSursa() {
            return contSursa;
        }
    }

    public static void main(String[] args) {

        // Minim 10 tranzacții, 3 luni diferite, CREDIT + DEBIT
        List<TranzactieExtinsa> lista = List.of(
                new TranzactieExtinsa(1, 1500.00, "2024-01-10", TipTranzactie.CREDIT, "CONT_A"),
                new TranzactieExtinsa(2, 200.00, "2024-01-15", TipTranzactie.DEBIT, "CONT_B"),
                new TranzactieExtinsa(3, 900.00, "2024-01-20", TipTranzactie.CREDIT, "CONT_A"),
                new TranzactieExtinsa(4, 1200.00, "2024-02-05", TipTranzactie.DEBIT, "CONT_C"),
                new TranzactieExtinsa(5, 300.00, "2024-02-10", TipTranzactie.CREDIT, "CONT_B"),
                new TranzactieExtinsa(6, 50.00, "2024-02-18", TipTranzactie.DEBIT, "CONT_A"),
                new TranzactieExtinsa(7, 2200.00, "2024-03-01", TipTranzactie.CREDIT, "CONT_D"),
                new TranzactieExtinsa(8, 100.00, "2024-03-02", TipTranzactie.DEBIT, "CONT_C"),
                new TranzactieExtinsa(9, 1750.00, "2024-03-15", TipTranzactie.CREDIT, "CONT_A"),
                new TranzactieExtinsa(10, 600.00, "2024-03-20", TipTranzactie.DEBIT, "CONT_B")
        );

        // -------------------------------------------------------------
        System.out.println("=== 1. Toate tranzacțiile CREDIT ===");
        lista.stream()
                .filter(t -> t.getTip() == TipTranzactie.CREDIT)
                .forEach(System.out::println);

        // -------------------------------------------------------------
        System.out.println("\n=== 2. Total procesat (sumă totală) ===");
        double total = lista.stream()
                .mapToDouble(Tranzactie::getSuma)
                .sum();
        System.out.printf("Total procesat: %.2f RON%n", total);

        // -------------------------------------------------------------
        System.out.println("\n=== 3. Sume per lună (yyyy-MM) ===");
        Map<String, Double> perLuna = lista.stream()
                .collect(Collectors.groupingBy(
                        t -> t.getData().substring(0, 7),
                        TreeMap::new,
                        Collectors.summingDouble(Tranzactie::getSuma)
                ));
        perLuna.forEach((luna, suma) ->
                System.out.printf("%s: %.2f RON%n", luna, suma));

        // -------------------------------------------------------------
        System.out.println("\n=== 4. Top 3 tranzacții (descrescător după sumă) ===");
        lista.stream()
                .sorted((a, b) -> Double.compare(b.getSuma(), a.getSuma()))
                .limit(3)
                .forEach(System.out::println);

        // -------------------------------------------------------------
        System.out.println("\n=== 5. Conturi sursă unice ===");
        List<String> conturi = lista.stream()
                .map(TranzactieExtinsa::getContSursa)
                .distinct()
                .collect(Collectors.toList());
        System.out.println("Conturi sursa unice: " + conturi);

        // -------------------------------------------------------------
        System.out.println("\n=== 6. Suma medie ===");
        double medie = lista.stream()
                .mapToDouble(Tranzactie::getSuma)
                .average()
                .orElse(0.0);
        System.out.printf("Suma medie: %.2f RON%n", medie);

        // -------------------------------------------------------------
        System.out.println("\n=== 7. Extras de cont lunar ===");
        Map<String, List<TranzactieExtinsa>> grupat = lista.stream()
                .collect(Collectors.groupingBy(
                        t -> t.getData().substring(0, 7),
                        TreeMap::new,
                        Collectors.toList()
                ));

        for (var entry : grupat.entrySet()) {
            String luna = entry.getKey();
            List<TranzactieExtinsa> tranz = entry.getValue();
            double sumaLuna = tranz.stream().mapToDouble(Tranzactie::getSuma).sum();

            System.out.printf("EXTRAS DE CONT - %s: %d tranzactii, total: %.2f RON%n",
                    luna, tranz.size(), sumaLuna);
        }
    }
}
