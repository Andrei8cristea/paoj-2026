package com.pao.laboratory11.exercise1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;

public class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        FraudEngine engine = new FraudEngine();

        int N = Integer.parseInt(br.readLine());

        for (int i = 0; i < N; i++) {
            String[] p = br.readLine().split(" ");
            int id = Integer.parseInt(p[0]);
            BigDecimal amount = new BigDecimal(p[1]);
            LocalDate date = LocalDate.parse(p[2]);
            String country = p[3];
            String channel = p[4];

            Transaction temp = new Transaction(id, amount, date, country, channel, 0, false);
            int score = RiskScorer.compute(temp);
            boolean flagged = Rules.isFlagged(score);

            Transaction tx = new Transaction(id, amount, date, country, channel, score, flagged);
            engine.add(tx);
        }

        int Q = Integer.parseInt(br.readLine());

        for (int i = 0; i < Q; i++) {
            String line = br.readLine();
            String[] p = line.split(" ");

            switch (p[0]) {

                case "CHECK" -> {
                    int id = Integer.parseInt(p[1]);
                    Transaction tx = engine.get(id);
                    if (tx == null) {
                        System.out.println("CHECK " + id + " => NOT_FOUND");
                    } else {
                        String verdict = tx.isFlagged() ? "FLAG" : "ALLOW";
                        System.out.println("CHECK " + id + " => " + verdict + " score=" + tx.getScore());
                    }
                }

                case "LIST_FLAGGED" -> {
                    var list = engine.listFlagged();
                    if (list.isEmpty()) {
                        System.out.println("NONE");
                    } else {
                        for (Transaction tx : list) {
                            System.out.println("[" + tx.getId() + "] FLAG score=" + tx.getScore());
                        }
                    }
                }

                case "TOP_RISK" -> {
                    int k = Integer.parseInt(p[1]);
                    var list = engine.topRisk(k);
                    for (Transaction tx : list) {
                        String verdict = tx.isFlagged() ? "FLAG" : "ALLOW";
                        System.out.println("[" + tx.getId() + "] " + verdict + " score=" + tx.getScore());
                    }
                }

                default -> System.out.println("ERR UNKNOWN_COMMAND");
            }
        }
    }
}
