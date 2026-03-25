package com.pao.laboratory05.biblioteca;

import java.util.Comparator;

public class CarteAutorComparator implements Comparator<Carte> {

    @Override
    public int compare(Carte carte1, Carte carte2){
        return carte1.getAutor().compareTo(carte2.getAutor());
    }
}
