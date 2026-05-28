package com.pao.laboratory11.exercise1;

import java.math.BigDecimal;
import java.util.Set;

public final class RiskScorer {

    private static final Set<String> HIGH_RISK_COUNTRIES = Set.of("RU", "NG", "IR", "KP", "SY");

    public static int compute(Transaction tx) {
        return amountScore(tx.getAmount())
                + countryScore(tx.getCountry())
                + channelScore(tx.getChannel());
    }

    private static int amountScore(BigDecimal amount) {
        double a = amount.doubleValue();

        if (a >= 5000) return 70;
        if (a >= 1000) return 40;
        if (a >= 500) return 20;
        if (a <= 100) return 5;
        return 0;
    }

    private static int countryScore(String country) {
        return HIGH_RISK_COUNTRIES.contains(country) ? 25 : 0;
    }

    private static int channelScore(String channel) {
        return switch (channel) {
            case "WEB" -> 15;
            case "APP" -> 10;
            case "CRYPTO" -> 30;
            case "POS" -> 5;
            case "ATM" -> 0;
            default -> 0;
        };
    }
}
