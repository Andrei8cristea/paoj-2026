package com.pao.laboratory05.biblioteca;

import java.util.Comparator;

public class CarteAnComparator implements Comparator<Carte> {

    @Override
    public int compare(Carte carte1, Carte carte2){
        return Integer.compare(carte1.getAn(), carte2.getAn());
    }

}
