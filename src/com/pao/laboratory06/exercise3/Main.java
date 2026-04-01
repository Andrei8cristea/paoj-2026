package com.pao.laboratory06.exercise3;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        System.out.println("=== Sortare Ingineri ===");

        Inginer[] ingineri = {
                new Inginer("Popescu", "Ana", "0711111111", 8000),
                new Inginer("Ionescu", "Vlad", "0722222222", 7000),
                new Inginer("Marin", "Ioana", null, 9000)
        };

        System.out.println("\n-- Sortare naturală (după nume) --");
        Arrays.sort(ingineri);
        for (Inginer i : ingineri) System.out.println(i.getNumeComplet());

        System.out.println("\n-- Sortare după salariu (descrescător) --");
        Arrays.sort(ingineri, new ComparatorInginerSalariu());
        for (Inginer i : ingineri) System.out.println(i.getNumeComplet() + " salariu=" + i.getSalariu());

        System.out.println("\n=== Demonstrație PlataOnline ===");
        PlataOnline po = new Inginer("Georgescu", "Mihai", "0733333333", 6000);
        po.autentificare("user", "pass");
        System.out.println("Sold: " + po.consultareSold());
        System.out.println("Plată 1000: " + po.efectuarePlata(1000));

        System.out.println("\n=== Demonstrație PlataOnlineSMS ===");
        PersoanaJuridica pj = new PersoanaJuridica("TechCorp", "SRL", "0744444444");
        pj.autentificare("firma", "parola");
        pj.trimiteSMS("Plata efectuată.");
        pj.trimiteSMS("Factura emisă.");
        System.out.println("SMS trimise: " + pj.getSmsTrimise());

        System.out.println("\n=== Edge cases ===");

        System.out.println("SMS fără telefon:");
        PersoanaJuridica pj2 = new PersoanaJuridica("FaraTelefon", "SRL", null);
        System.out.println("Trimis? " + pj2.trimiteSMS("Salut"));

        System.out.println("\nAutentificare invalidă:");
        try {
            po.autentificare(null, "123");
        } catch (Exception e) {
            System.out.println("Eroare: " + e.getMessage());
        }

        System.out.println("\n=== Constante financiare ===");
        System.out.println("TVA = " + ConstanteFinanciare.TVA.getValoare());
    }
}
