package com.pao.laboratory09.exercise1;

import java.io.*;
import java.util.*;

public class Main {

    private static final String FILE_PATH = "output/lab09_ex1.ser";

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int N = Integer.parseInt(sc.nextLine());
        List<Tranzactie> tranzactii = new ArrayList<>();

        // Citire tranzacții
        for (int i = 0; i < N; i++) {
            int id = sc.nextInt();
            double suma = sc.nextDouble();
            String data = sc.next();
            String contSursa = sc.next();
            String contDestinatie = sc.next();
            String tipStr = sc.next();

            TipTranzactie tip = TipTranzactie.valueOf(tipStr);

            Tranzactie t = new Tranzactie(id, suma, data, contSursa, contDestinatie, tip);
            t.setNote("procesat"); // înainte de serializare
            tranzactii.add(t);
        }

        // Creează folderul output dacă nu există
        File dir = new File("output");
        if (!dir.exists()) {
            dir.mkdirs();
        }

// Serializare
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(tranzactii);
        } catch (IOException e) {
            e.printStackTrace();
        }


        // Deserializare
        List<Tranzactie> lista = null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            lista = (List<Tranzactie>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        // Procesare comenzi
        sc.nextLine(); // consumă newline

        while (sc.hasNextLine()) {
            String line = sc.nextLine().trim();
            if (line.isEmpty()) continue;

            String[] parts = line.split(" ");

            switch (parts[0]) {

                case "LIST":
                    for (Tranzactie t : lista) {
                        System.out.println(t);
                    }
                    break;

                case "FILTER":
                    String prefix = parts[1]; // yyyy-MM
                    boolean found = false;
                    for (Tranzactie t : lista) {
                        if (t.getData().startsWith(prefix)) {
                            System.out.println(t);
                            found = true;
                        }
                    }
                    if (!found) {
                        System.out.println("Niciun rezultat.");
                    }
                    break;

                case "NOTE":
                    int id = Integer.parseInt(parts[1]);
                    Tranzactie foundT = null;
                    for (Tranzactie t : lista) {
                        if (t.getId() == id) {
                            foundT = t;
                            break;
                        }
                    }
                    if (foundT == null) {
                        System.out.println("NOTE[" + id + "]: not found");
                    } else {
                        System.out.println("NOTE[" + id + "]: " + foundT.getNote());
                    }
                    break;
            }
        }
    }
}
