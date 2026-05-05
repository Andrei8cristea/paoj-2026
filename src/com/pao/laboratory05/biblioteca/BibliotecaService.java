package com.pao.laboratory05.biblioteca;

import java.util.Arrays;
import java.util.Comparator;

public class BibliotecaService {

    private Carte[] carti = new Carte[0];

    private BibliotecaService() {}

    private static class Holder {
        private static final BibliotecaService INSTANCE = new BibliotecaService();
    }

    public static BibliotecaService getInstance() {
        return Holder.INSTANCE;
    }

    public void addCarte(Carte carte){
        Carte[] newArray = new Carte[carti.length + 1];
        System.arraycopy(carti, 0, newArray, 0, carti.length);
        newArray[carti.length] = carte;
        carti = newArray;

        System.out.println("Carte adaugata: " + carte.getTitlu());
    }
    public void listSortedByRating() {
        Carte[] copy = carti.clone();
        Arrays.sort(copy);
        printList(copy);
    }

    public void listSortedBy(Comparator<Carte> comparator) {
        Carte[] copy = carti.clone();
        Arrays.sort(copy, comparator);
        printList(copy);
    }

    private void printList(Carte[] list) {
        int index = 1;
        for (Carte c : list) {
            System.out.println(index + ". " + c);
            index++;
        }
    }

}
