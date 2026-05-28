package com.pao.laboratory11.exercise3;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Demo for bonus exercise 3: custom Collector + immutable Snapshot.
 * Uses hardcoded data and performs 3 queries on the resulting snapshot.
 */
public class Main {
    public static void main(String[] args) {

        // Hardcoded transaction data with tie-breakers for stability testing
        List<Transaction> data = List.of(
                new Transaction(1, new BigDecimal("1200.00"), LocalDate.of(2026, 5, 1), "RO", "WEB"),
                new Transaction(2, new BigDecimal("90.00"),   LocalDate.of(2026, 5, 1), "RU", "ATM"),
                new Transaction(3, new BigDecimal("6000.00"), LocalDate.of(2026, 5, 2), "NG", "APP"),
                new Transaction(4, new BigDecimal("500.00"),  LocalDate.of(2026, 5, 3), "RO", "POS"),
                new Transaction(5, new BigDecimal("1200.00"), LocalDate.of(2026, 6, 1), "IR", "WEB"),
                new Transaction(6, new BigDecimal("300.00"),  LocalDate.of(2026, 6, 5), "RO", "WEB"),
                new Transaction(7, new BigDecimal("6000.00"), LocalDate.of(2026, 6, 10),"KP", "CRYPTO")
        );

        // Collect into an immutable Snapshot (top 5 by amount desc, then id asc)
        Snapshot snap = data.stream().collect(CustomCollectors.toSnapshot(5));

        // ── Query 1: Top transactions by amount ─────────────────────────
        System.out.println("=== Query 1: Top 5 transactions (by amount desc, id asc) ===");
        for (Transaction tx : snap.getTopTransactions()) {
            System.out.println("  " + tx);
        }

        // ── Query 2: Transaction count by country (sorted desc by count, then alphabetic) ──
        System.out.println();
        System.out.println("=== Query 2: Count by country (desc count, asc name) ===");
        snap.getCountByCountry().entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue(Comparator.reverseOrder())
                        .thenComparing(Map.Entry.comparingByKey()))
                .forEach(e -> System.out.println("  " + e.getKey() + " -> " + e.getValue()));

        // ── Query 3: Channel ranking (desc count, asc name) ─────────────
        System.out.println();
        System.out.println("=== Query 3: Channel ranking (desc count, asc name) ===");
        snap.getCountByChannel().entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue(Comparator.reverseOrder())
                        .thenComparing(Map.Entry.comparingByKey()))
                .forEach(e -> System.out.println("  " + e.getKey() + " -> " + e.getValue()));

        // ── Summary ─────────────────────────────────────────────────────
        System.out.println();
        System.out.println("=== Total amount across all transactions ===");
        System.out.println("  " + snap.getTotalAmount());
    }
}
