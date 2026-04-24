package com.pao.laboratory08.exercise2;

import com.pao.laboratory08.exercise1.Student;
import com.pao.laboratory08.exercise1.Adresa;

import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        // 1. Citim studenții din fișier (ca în exercițiul 1)
        List<Student> studenti = citesteStudenti();

        // 2. Citim pragul de vârstă
        Scanner scanner = new Scanner(System.in);
        int prag = scanner.nextInt();

        // 3. Filtrăm
        List<Student> filtrati = new ArrayList<>();
        for (Student s : studenti) {
            if (s.getVarsta() >= prag) {
                filtrati.add(s);
            }
        }

        // 4. Scriem în rezultate.txt
        scrieInFisier(filtrati);

        // 5. Afișăm sumarul
        System.out.println("Filtru: varsta >= " + prag);
        System.out.println("Rezultate: " + filtrati.size() + " studenti");
        System.out.println();

        for (Student s : filtrati) {
            System.out.println(s);
        }

        System.out.println();
        System.out.println("Scris in: rezultate.txt");
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

    private static void scrieInFisier(List<Student> studenti) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("rezultate.txt"))) {

            for (Student s : studenti) {
                bw.write(s.toString());
                bw.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
