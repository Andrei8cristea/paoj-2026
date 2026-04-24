package com.pao.laboratory08.exercise1;

import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Citim prima linie NON-goală
        String commandLine = "";
        while (commandLine.isEmpty() && scanner.hasNextLine()) {
            commandLine = scanner.nextLine().trim();
        }

        List<Student> studenti = citesteStudenti();

        // Dacă e PRINT
        if (commandLine.equals("PRINT")) {
            for (Student s : studenti) {
                System.out.println(s);
            }
            return;
        }

        // SHALLOW / DEEP
        String[] parts = commandLine.split(" ", 2);
        String command = parts[0];

        String nume = "";
        if (parts.length > 1) {
            nume = parts[1].trim();
        }

        Student target = null;
        for (Student s : studenti) {
            if (s.getNume().equals(nume)) {
                target = s;
                break;
            }
        }

        if (target == null) return;

        try {
            if (command.equals("SHALLOW")) {
                Student clone = target.shallowClone();
                clone.getAdresa().setOras("MODIFICAT");

                System.out.println("Original: " + target);
                System.out.println("Clona: " + clone);
            }

            if (command.equals("DEEP")) {
                Student clone = target.deepClone();
                clone.getAdresa().setOras("MODIFICAT");

                System.out.println("Original: " + target);
                System.out.println("Clona: " + clone);
            }

        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

    private static List<Student> citesteStudenti() {
        List<Student> list = new ArrayList<>();

        try (InputStream input = Main.class.getClassLoader()
                .getResourceAsStream("com/pao/laboratory08/tests/studenti.txt")) {

            if (input == null) {
                System.err.println("Nu am găsit fisierul studenti.txt");
                return list;
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(input));
            String line;

            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] p = line.split(",");
                String nume = p[0].trim();
                int varsta = Integer.parseInt(p[1].trim());
                String oras = p[2].trim();
                String strada = p[3].trim();

                Adresa adresa = new Adresa(oras, strada);
                Student s = new Student(nume, varsta, adresa);
                list.add(s);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }
}
