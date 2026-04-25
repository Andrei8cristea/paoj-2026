package com.pao.project;

import com.pao.project.exception.MedicNotFoundException;
import com.pao.project.exception.PacientNotFoundException;
import com.pao.project.exception.ProgramareConflictException;
import com.pao.project.model.*;
import com.pao.project.service.MedicService;
import com.pao.project.service.PacientService;
import com.pao.project.service.ProgramareService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        PacientService pacientService = PacientService.getInstance();
        MedicService medicService = MedicService.getInstance();
        ProgramareService programareService = ProgramareService.getInstance();

        // Creăm cabinetul medical
        CabinetMedical cabinet = new CabinetMedical(
                "MedLife Central", "Str. Victoriei 12, București", "021-300-0000");
        System.out.println("=== Cabinet: " + cabinet + " ===\n");

        // ─────────────────────────────────────────────
        // 1. Înregistrează pacienți noi în sistem
        // ─────────────────────────────────────────────
        System.out.println("--- 1. Înregistrare pacienți ---");
        Pacient p1 = new Pacient("P1", "Ionescu", "Ana", "1234567890123", "0711111111");
        Pacient p2 = new Pacient("P2", "Popescu", "Mihai", "2234567890123", "0722222222");
        Pacient p3 = new Pacient("P3", "Dumitrescu", "Maria", "3234567890123", "0733333333");
        pacientService.adaugaPacient(p1);
        pacientService.adaugaPacient(p2);
        pacientService.adaugaPacient(p3);
        System.out.println("Pacienți înregistrați: " + pacientService.listeazaToti());
        System.out.println();

        // ─────────────────────────────────────────────
        // 2. Adaugă medici noi în cabinet
        //    (inclusiv un MedicSpecialist — nivel 2 de moștenire)
        // ─────────────────────────────────────────────
        System.out.println("--- 2. Adăugare medici ---");
        Medic m1 = new Medic("M1", "Georgescu", "Andrei", Specializare.MEDICINA_FAMILIE);
        MedicSpecialist m2 = new MedicSpecialist(
                "M2", "Marin", "Raluca", Specializare.CARDIOLOGIE,
                "Ecocardiografie", 12);
        Medic m3 = new Medic("M3", "Stanciu", "Elena", Specializare.DERMATOLOGIE);

        medicService.adaugaMedic(m1);
        medicService.adaugaMedic(m2);  // MedicSpecialist funcționează polimorfic ca Medic
        medicService.adaugaMedic(m3);

        cabinet.adaugaMedic(m1);
        cabinet.adaugaMedic(m2);
        cabinet.adaugaMedic(m3);

        System.out.println("Medici adăugați: " + medicService.listeazaToti());
        System.out.println("Rol m1: " + m1.getRol());          // "Medic"
        System.out.println("Rol m2: " + m2.getRol());          // "Medic Specialist"
        System.out.println("Competență m2: " + m2.getCompetenta()
                + " (" + m2.getAniExperienta() + " ani experiență)");
        System.out.println("Medici în cabinet: " + cabinet.numarMedici());
        System.out.println();

        // ─────────────────────────────────────────────
        // 3. Programează consultații pentru pacienți
        // ─────────────────────────────────────────────
        System.out.println("--- 3. Programare consultații ---");
        try {
            // Ana Ionescu la dr. Georgescu (medicină de familie), 25 aprilie ora 10
            programareService.programeaza(
                    "PR1", p1, m1, LocalDateTime.of(2025, 4, 25, 10, 0));
            System.out.println("PR1 creată: Ana Ionescu → dr. Georgescu, 25.04 10:00");

            // Mihai Popescu la dr. Georgescu, 25 aprilie ora 11
            programareService.programeaza(
                    "PR2", p2, m1, LocalDateTime.of(2025, 4, 25, 11, 0));
            System.out.println("PR2 creată: Mihai Popescu → dr. Georgescu, 25.04 11:00");

            // Ana Ionescu la dr. Marin (cardiolog specialist), 26 aprilie ora 9:30
            programareService.programeaza(
                    "PR3", p1, m2, LocalDateTime.of(2025, 4, 26, 9, 30));
            System.out.println("PR3 creată: Ana Ionescu → dr. Marin (specialist), 26.04 09:30");

            // Maria Dumitrescu la dr. Stanciu (dermatologie), 25 aprilie ora 14
            programareService.programeaza(
                    "PR4", p3, m3, LocalDateTime.of(2025, 4, 25, 14, 0));
            System.out.println("PR4 creată: Maria Dumitrescu → dr. Stanciu, 25.04 14:00");

        } catch (ProgramareConflictException e) {
            System.out.println("Eroare programare: " + e.getMessage());
        }

        // Testăm conflict: încercăm să programăm alt pacient la dr. Georgescu, aceeași oră
        try {
            programareService.programeaza(
                    "PR5", p3, m1, LocalDateTime.of(2025, 4, 25, 10, 0));
        } catch (ProgramareConflictException e) {
            System.out.println("Conflict detectat corect: " + e.getMessage());
        }
        System.out.println();

        // ─────────────────────────────────────────────
        // 4. Anulează o programare
        // ─────────────────────────────────────────────
        System.out.println("--- 4. Anulare programare ---");
        programareService.anuleazaProgramare("PR2");
        System.out.println("Programarea PR2 (Mihai Popescu la dr. Georgescu) a fost anulată.");
        System.out.println();

        // ─────────────────────────────────────────────
        // 5. Caută pacient după CNP
        // ─────────────────────────────────────────────
        System.out.println("--- 5. Căutare pacient după CNP ---");
        try {
            Pacient gasit = pacientService.cautaDupaCnp("1234567890123");
            System.out.println("Pacient găsit: " + gasit);
        } catch (PacientNotFoundException e) {
            System.out.println(e.getMessage());
        }

        // Testăm cu un CNP inexistent
        try {
            pacientService.cautaDupaCnp("0000000000000");
        } catch (PacientNotFoundException e) {
            System.out.println("CNP inexistent: " + e.getMessage());
        }
        System.out.println();

        // ─────────────────────────────────────────────
        // 6. Caută medic după specializare
        // ─────────────────────────────────────────────
        System.out.println("--- 6. Căutare medic după specializare ---");
        List<Medic> cardiologi = medicService.cautaDupaSpecializare(Specializare.CARDIOLOGIE);
        System.out.println("Medici cardiologi: " + cardiologi);

        // Testăm MedicNotFoundException — căutăm un medic inexistent după ID
        try {
            medicService.cautaDupaId("M99");
        } catch (MedicNotFoundException e) {
            System.out.println("Medic inexistent: " + e.getMessage());
        }
        System.out.println();

        // ─────────────────────────────────────────────
        // 7. Listează toate programările dintr-o zi
        // ─────────────────────────────────────────────
        System.out.println("--- 7. Programări din 25.04.2025 ---");
        List<Programare> dinZi = programareService.programariDinZi(LocalDate.of(2025, 4, 25));
        for (Programare p : dinZi) {
            System.out.println("  " + p);
        }
        System.out.println();

        // ─────────────────────────────────────────────
        // 8. Marchează o consultație ca finalizată
        // ─────────────────────────────────────────────
        System.out.println("--- 8. Finalizare consultații ---");
        Consultatie c1 = programareService.finalizeazaConsultatie(
                "C1", "PR1", "Răceală ușoară", "Hidratare, repaus, paracetamol");
        System.out.println("Consultație finalizată: " + c1);

        Consultatie c2 = programareService.finalizeazaConsultatie(
                "C2", "PR3", "Aritmie sinusală benignă", "Control peste 6 luni, evitare stres");
        System.out.println("Consultație finalizată: " + c2);
        System.out.println();

        // ─────────────────────────────────────────────
        // 9. Afișează istoricul consultațiilor unui pacient
        // ─────────────────────────────────────────────
        System.out.println("--- 9. Istoric consultații pentru Ana Ionescu ---");
        List<Consultatie> istoricAna = programareService.istoricConsultatiiPacient(p1);
        for (Consultatie c : istoricAna) {
            System.out.println("  " + c);
        }
        System.out.println();

        // ─────────────────────────────────────────────
        // 10. Listează medicii ordonați alfabetic
        // ─────────────────────────────────────────────
        System.out.println("--- 10. Medici ordonați alfabetic ---");
        List<Medic> mediciSortati = medicService.listeazaSortatAlfabetic();
        for (Medic m : mediciSortati) {
            System.out.println("  " + m);
        }
        System.out.println();

        // ─────────────────────────────────────────────
        // 11. Afișează programările unui medic
        // ─────────────────────────────────────────────
        System.out.println("--- 11. Programări pentru dr. Georgescu ---");
        List<Programare> programariM1 = programareService.programariPentruMedic(m1);
        for (Programare p : programariM1) {
            System.out.println("  " + p);
        }
        System.out.println();

        // ─────────────────────────────────────────────
        // 12. Elimină un pacient din sistem
        // ─────────────────────────────────────────────
        System.out.println("--- 12. Eliminare pacient ---");
        try {
            pacientService.stergePacient("P2");
            System.out.println("Pacientul Mihai Popescu (P2) a fost eliminat din sistem.");
        } catch (PacientNotFoundException e) {
            System.out.println(e.getMessage());
        }

        // Verificăm că nu mai apare
        System.out.println("Pacienți rămași: " + pacientService.listeazaToti());

        // Încercăm să ștergem din nou — demonstrăm excepția
        try {
            pacientService.stergePacient("P2");
        } catch (PacientNotFoundException e) {
            System.out.println("Ștergere repetată: " + e.getMessage());
        }

        System.out.println("\n=== Demonstrație completă finalizată ===");
    }
}
