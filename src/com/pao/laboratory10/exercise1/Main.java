package com.pao.laboratory10.exercise1;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        LinkedList<Tranzactie> coada = new LinkedList<>();

        while (sc.hasNext()) {
            String cmd = sc.next();

            switch (cmd) {

                case "ENQUEUE": {
                    int id = sc.nextInt();
                    double suma = Double.parseDouble(sc.next());
                    String data = sc.next();
                    TipTranzactie tip = TipTranzactie.valueOf(sc.next());
                    coada.addLast(new Tranzactie(id, suma, data, tip));
                    break;
                }

                case "DEQUEUE": {
                    if (coada.isEmpty()) {
                        System.out.println("Coada goala.");
                    } else {
                        Tranzactie t = coada.removeFirst();
                        System.out.println("Procesat: " + t);
                    }
                    break;
                }

                case "PUSH": {
                    int id = sc.nextInt();
                    double suma = Double.parseDouble(sc.next());
                    String data = sc.next();
                    TipTranzactie tip = TipTranzactie.valueOf(sc.next());
                    coada.addFirst(new Tranzactie(id, suma, data, tip));
                    break;
                }

                case "POP": {
                    if (coada.isEmpty()) {
                        System.out.println("Coada goala.");
                    } else {
                        Tranzactie t = coada.removeFirst();
                        System.out.println("Extras: " + t);
                    }
                    break;
                }

                case "PRINT": {
                    for (Tranzactie t : coada) {
                        System.out.println(t);
                    }
                    break;
                }

                case "SIZE": {
                    System.out.println("Dimensiune coada: " + coada.size());
                    break;
                }

                case "REMOVE_DEBIT": {
                    int count = 0;
                    Iterator<Tranzactie> it = coada.iterator();
                    while (it.hasNext()) {
                        Tranzactie t = it.next();
                        if (t.getTip() == TipTranzactie.DEBIT) {
                            it.remove();
                            count++;
                        }
                    }
                    System.out.println("Eliminat " + count + " tranzactii DEBIT.");
                    break;
                }

                case "REMOVE_BELOW": {
                    double threshold = Double.parseDouble(sc.next());
                    int count = 0;
                    Iterator<Tranzactie> it = coada.iterator();
                    while (it.hasNext()) {
                        Tranzactie t = it.next();
                        if (t.getSuma() < threshold) {
                            it.remove();
                            count++;
                        }
                    }
                    System.out.printf("Eliminat %d tranzactii sub %.2f RON.%n", count, threshold);
                    break;
                }

                default:
                    // ignoră comenzi invalide
                    break;
            }
        }
    }
}
