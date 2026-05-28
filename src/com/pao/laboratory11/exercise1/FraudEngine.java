package com.pao.laboratory11.exercise1;

import java.util.*;

public final class FraudEngine {

    private final Map<Integer, Transaction> map = new HashMap<>();
    private final List<Transaction> all = new ArrayList<>();

    private final Comparator<Transaction> cmp =
            Comparator.comparingInt(Transaction::getScore).reversed()
                    .thenComparingInt(Transaction::getId);

    public void add(Transaction tx) {
        map.put(tx.getId(), tx);
        all.add(tx);
    }

    public Transaction get(int id) {
        return map.get(id);
    }

    public List<Transaction> listFlagged() {
        List<Transaction> out = new ArrayList<>();
        for (Transaction tx : all) if (tx.isFlagged()) out.add(tx);
        out.sort(cmp);
        return out;
    }

    public List<Transaction> topRisk(int k) {
        List<Transaction> out = new ArrayList<>(all);
        out.sort(cmp);
        if (k > out.size()) return out;
        return out.subList(0, k);
    }
}
