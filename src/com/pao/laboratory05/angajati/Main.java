package com.pao.laboratory05.angajati;

import java.util.Scanner;

/**
 * Exercise 3 — Angajați
 *
 * Cerințele complete se află în:
 *   src/com/pao/laboratory05/Readme.md  →  secțiunea "Exercise 3 — Angajați"
 *
 * Creează fișierele de la zero în acest pachet, apoi rulează Main.java
 * pentru a verifica output-ul așteptat din Readme.
 */
public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        AngajatService service = AngajatService.getInstance();

        while (true) {
            System.out.println("\n===== Gestionare Angajați =====");
            System.out.println("1. Adaugă angajat");
            System.out.println("2. Listare după salariu");
            System.out.println("3. Caută după departament");
            System.out.println("0. Ieșire");
            System.out.print("Opțiune: ");

            int opt = sc.nextInt();
            sc.nextLine(); // consumă newline

            switch (opt) {
                case 1 -> {
                    System.out.print("Nume: ");
                    String nume = sc.nextLine();

                    System.out.print("Departament (nume): ");
                    String numeDept = sc.nextLine();

                    System.out.print("Departament (locatie): ");
                    String locatieDept = sc.nextLine();

                    System.out.print("Salariu: ");
                    double salariu = sc.nextDouble();
                    sc.nextLine();

                    Departament d = new Departament(numeDept, locatieDept);
                    Angajat a = new Angajat(nume, d, salariu);

                    service.addAngajat(a);
                }

                case 2 -> service.listBySalary();

                case 3 -> {
                    System.out.print("Departament: ");
                    String dept = sc.nextLine();
                    service.findByDepartament(dept);
                }

                case 0 -> {
                    System.out.println("La revedere!");
                    return;
                }

                default -> System.out.println("Opțiune invalidă!");
            }
        }
    }
}
