package com.pao.laboratory11.exercise3;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collector;

/**
 * Provides a custom Collector that aggregates transactions into an immutable Snapshot.
 */
public final class CustomCollectors {

    private CustomCollectors() {}

    /**
     * Returns a Collector that accumulates Transaction elements into a Snapshot.
     *
     * @param topN number of top transactions (by amount desc, then id asc) to include
     * @return a Collector producing an immutable Snapshot
     */
    public static Collector<Transaction, ?, Snapshot> toSnapshot(int topN) {

        // Mutable accumulator used during collection
        class Agg {
            final Map<String, Long> countByCountry = new HashMap<>();
            final Map<String, Long> countByChannel = new HashMap<>();
            BigDecimal total = BigDecimal.ZERO;
            final List<Transaction> all = new ArrayList<>();

            void accumulate(Transaction tx) {
                countByCountry.merge(tx.getCountry(), 1L, Long::sum);
                countByChannel.merge(tx.getChannel(), 1L, Long::sum);
                total = total.add(tx.getAmount());
                all.add(tx);
            }

            Agg combine(Agg other) {
                other.countByCountry.forEach((k, v) -> countByCountry.merge(k, v, Long::sum));
                other.countByChannel.forEach((k, v) -> countByChannel.merge(k, v, Long::sum));
                total = total.add(other.total);
                all.addAll(other.all);
                return this;
            }

            Snapshot finish() {
                // Sort by amount desc, then id asc for deterministic top-N
                all.sort(Comparator.comparing(Transaction::getAmount).reversed()
                        .thenComparingInt(Transaction::getId));
                List<Transaction> top = all.subList(0, Math.min(topN, all.size()));
                return new Snapshot(countByCountry, countByChannel, total, top);
            }
        }

        return Collector.of(
                Agg::new,
                Agg::accumulate,
                Agg::combine,
                Agg::finish
        );
    }
}
