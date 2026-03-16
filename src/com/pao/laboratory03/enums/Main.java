package com.pao.laboratory03.enums;

import com.pao.laboratory03.Ex2EnumsSolution;

/**
 * Exercițiul 2 — Enum-uri
 *
 * Creează în acest pachet (lângă acest Main.java) un enum și apoi folosește-l aici.
 *
 * PASUL 1 — Creează enum-ul Priority.java (fișier separat în același pachet):
 *   - Constante: LOW, MEDIUM, HIGH, CRITICAL
 *   - Câmpuri private: int level, String color
 *   - Constructor privat: Priority(int level, String color)
 *   - Getteri: getLevel(), getColor()
 *   - Metodă abstractă: String getEmoji() — fiecare constantă o implementează diferit
 *     LOW → "🟢", MEDIUM → "🟡", HIGH → "🟠", CRITICAL → "🔴"
 *   - Valorile sugerate:
 *     LOW(1, "green"), MEDIUM(2, "yellow"), HIGH(3, "orange"), CRITICAL(4, "red")
 *
 * PASUL 2 — În acest Main.java:
 *   a) Parcurge toate valorile cu Priority.values() și afișează:
 *      "emoji name (level=X, color=Y)"
 *   b) Folosește switch pe un Priority și afișează un mesaj specific.
 *   c) Convertește un String în Priority cu Priority.valueOf("HIGH") — afișează rezultatul.
 *   d) Demonstrează compararea: folosește == între două enum-uri (NU .equals()).
 *   e) Afișează name() și ordinal() pentru fiecare constantă.
 *
 * Output așteptat:
 *
 * === Toate prioritățile ===
 * 🟢 LOW (level=1, color=green)
 * 🟡 MEDIUM (level=2, color=yellow)
 * 🟠 HIGH (level=3, color=orange)
 * 🔴 CRITICAL (level=4, color=red)
 *
 * === Switch pe prioritate ===
 * ⚠️ Atenție! Prioritate ridicată!
 *
 * === valueOf ===
 * Priority.valueOf("HIGH") = HIGH
 *
 * === Comparare enum ===
 * HIGH == HIGH? true
 * HIGH == LOW? false
 *
 * === name() și ordinal() ===
 * LOW: name=LOW, ordinal=0
 * MEDIUM: name=MEDIUM, ordinal=1
 * HIGH: name=HIGH, ordinal=2
 * CRITICAL: name=CRITICAL, ordinal=3
 */
public class Main {

    private enum Priority {
        LOW(1, "green") {
            @Override public String getEmoji() { return "🟢"; }
        },
        MEDIUM(2, "yellow"){
            @Override public String getEmoji() { return "\uD83D\uDFE1"; }
        },
        HIGH(3, "orange"){
            @Override public String getEmoji() { return "\uD83D\uDFE0"; }
        },
        CRITICAL(4, "red"){
            @Override public String getEmoji() { return "\uD83D\uDD34"; }
        };

        private final int level;
        private final String color;

        Priority(int level, String color){
            this.level = level;
            this.color = color;
        }

        public int getLevel(){return level;}
        public String getColor(){return color;}
        public abstract String getEmoji();
    }

    public static void main(String[] args) {
        // A
        //"emoji name (level=X, color=Y)"
        for (Priority p : Priority.values()){
            System.out.println(p.getEmoji() + " " +p.name() +
                    " (level=" + p.getLevel() + ", color=" +
                    p.getColor() + ")\n");
        }

        //B
        Priority test = Priority.MEDIUM;
        switch(test){
            case LOW: System.out.println("OK"); break;
            case MEDIUM: System.out.println("Asa si asa"); break;
            case HIGH: System.out.println("Grav"); break;
            case CRITICAL: System.out.println("FFFF grav!!!!"); break;
        }

        //c
        Priority fromString = Priority.valueOf("MEDIUM");
        System.out.println("Priority.valueOf(\"MEDIUM\") = " + fromString);

        //d
        System.out.println("MEDIUM == MEDIUM? " + (Priority.MEDIUM == fromString));

        //e

        //LOW: name=LOW, ordinal=0
        for (Priority p : Priority.values()){
            System.out.println(p.name() + ": name=" + p.name() +
                    ", ordinal=" + p.ordinal());
        }
    }
}

