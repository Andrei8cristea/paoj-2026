package com.pao.laboratory10.exercise2;

import com.pao.laboratory10.exercise1.Tranzactie;
import com.pao.laboratory10.exercise1.TipTranzactie;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        List<Tranzactie> lista = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            int id = sc.nextInt();
            double suma = Double.parseDouble(sc.next());
            String data = sc.next();
            TipTranzactie tip = TipTranzactie.valueOf(sc.next());
            lista.add(new Tranzactie(id, suma, data, tip));
        }

        while (sc.hasNext()) {
            String cmd = sc.next();

            switch (cmd) {

                case "UNIQUE_IDS": {
                    LinkedHashSet<Integer> set = new LinkedHashSet<>();
                    for (Tranzactie t : lista) {
                        set.add(t.getId());
                    }
                    System.out.print("IDs unice (" + set.size() + "): [");
                    int k = 0;
                    for (int id : set) {
                        System.out.print(id);
                        if (k < set.size() - 1) System.out.print(", ");
                        k++;
                    }
                    System.out.println("]");
                    break;
                }

                case "MONTHLY_REPORT": {
                    TreeMap<String, double[]> map = new TreeMap<>();

                    for (Tranzactie t : lista) {
                        String luna = t.getData().substring(0, 7); // yyyy-MM
                        map.putIfAbsent(luna, new double[]{0.0, 0.0});
                        if (t.getTip() == TipTranzactie.CREDIT) {
                            map.get(luna)[0] += t.getSuma();
                        } else {
                            map.get(luna)[1] += t.getSuma();
                        }
                    }

                    for (Map.Entry<String, double[]> e : map.entrySet()) {
                        String luna = e.getKey();
                        double[] sume = e.getValue();
                        System.out.printf("%s: CREDIT %.2f RON, DEBIT %.2f RON%n",
                                luna, sume[0], sume[1]);
                    }
                    break;
                }

                case "TOP": {
                    int n = sc.nextInt();
                    List<Tranzactie> copie = new ArrayList<>(lista);

                    copie.sort((a, b) -> Double.compare(b.getSuma(), a.getSuma()));

                    System.out.println("Top " + n + ":");
                    for (int i = 0; i < Math.min(n, copie.size()); i++) {
                        System.out.println(copie.get(i));
                    }
                    break;
                }

                case "SORT_ASC": {
                    lista.sort(Comparator.comparingDouble(Tranzactie::getSuma));
                    for (Tranzactie t : lista) System.out.println(t);
                    break;
                }

                case "SORT_DESC": {
                    lista.sort((a, b) -> Double.compare(b.getSuma(), a.getSuma()));
                    for (Tranzactie t : lista) System.out.println(t);
                    break;
                }

                case "REVERSE": {
                    Collections.reverse(lista);
                    for (Tranzactie t : lista) System.out.println(t);
                    break;
                }

                case "MIN_MAX": {
                    Tranzactie min = Collections.min(lista, Comparator.comparingDouble(Tranzactie::getSuma));
                    Tranzactie max = Collections.max(lista, Comparator.comparingDouble(Tranzactie::getSuma));

                    System.out.println("MIN: " + min);
                    System.out.println("MAX: " + max);
                    break;
                }

                case "CME_DEMO": {
                    try {
                        for (Tranzactie t : lista) {
                            lista.remove(t); // declanșează CME
                        }
                    } catch (ConcurrentModificationException e) {
                        System.out.println("ConcurrentModificationException prins: modificare in iteratie detectata.");
                    }
                    break;
                }

                default:
                    // ignoră comenzi necunoscute
                    break;
            }
        }
    }
}
