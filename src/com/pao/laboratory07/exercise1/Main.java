package com.pao.laboratory07.exercise1;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        StareComanda stareCurenta = StareComanda.valueOf(in.nextLine().trim());
        System.out.println(stareCurenta);

        Deque<StareComanda> istoric = new ArrayDeque<>();

        boolean inStareFinala = (stareCurenta == StareComanda.LIVRATA ||
                stareCurenta == StareComanda.ANULATA);

        boolean aAfisatFinal = false;

        while (true) {
            String cmd = in.nextLine().trim();

            // QUIT
            if (cmd.equals("QUIT")) {
                if (inStareFinala && !aAfisatFinal) {
                    System.out.println("Comanda este in stare finala.");
                }
                break;
            }

            // Dacă suntem în stare finală și comanda NU este undo
            if (inStareFinala && !cmd.equals("undo")) {
                System.out.println("Comanda este in stare finala.");
                aAfisatFinal = true;   // IMPORTANT: ca să nu mai afișăm la QUIT
                continue;
            }

            switch (cmd) {

                case "next" -> {
                    istoric.push(stareCurenta);

                    switch (stareCurenta) {
                        case PLASATA -> stareCurenta = StareComanda.PROCESATA;
                        case PROCESATA -> stareCurenta = StareComanda.EXPEDIATA;
                        case EXPEDIATA -> stareCurenta = StareComanda.LIVRATA;
                        default -> {}
                    }

                    System.out.println(stareCurenta);

                    if (stareCurenta == StareComanda.LIVRATA) {
                        inStareFinala = true;
                        aAfisatFinal = false;
                    }
                }

                case "cancel" -> {
                    istoric.push(stareCurenta);

                    if (stareCurenta != StareComanda.LIVRATA &&
                            stareCurenta != StareComanda.ANULATA) {

                        stareCurenta = StareComanda.ANULATA;
                        System.out.println(stareCurenta);

                        inStareFinala = true;
                        aAfisatFinal = false;

                    } else {
                        System.out.println("Comanda este in stare finala.");
                        aAfisatFinal = true;
                    }
                }

                case "undo" -> {
                    if (!istoric.isEmpty()) {
                        stareCurenta = istoric.pop();
                    }

                    System.out.println(stareCurenta);

                    inStareFinala = (stareCurenta == StareComanda.LIVRATA ||
                            stareCurenta == StareComanda.ANULATA);

                    aAfisatFinal = false;
                }

                default -> {
                    // comenzi invalide ignorate
                }
            }
        }
    }
}
