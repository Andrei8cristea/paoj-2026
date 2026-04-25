package com.pao.project.service;

import com.pao.project.exception.ProgramareConflictException;
import com.pao.project.model.Consultatie;
import com.pao.project.model.Medic;
import com.pao.project.model.Pacient;
import com.pao.project.model.Programare;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class ProgramareService {

    private static ProgramareService instance;

    private Map<String, Programare> programariById = new HashMap<>();
    private List<Consultatie> consultatii = new ArrayList<>();

    private ProgramareService() {}

    public static ProgramareService getInstance() {
        if (instance == null) {
            instance = new ProgramareService();
        }
        return instance;
    }

    public Programare programeaza(String id, Pacient pacient, Medic medic, LocalDateTime dataOra)
            throws ProgramareConflictException {

        if (pacient == null || medic == null || dataOra == null) {
            throw new IllegalArgumentException("Parametri invalizi la programare.");
        }

        for (Programare p : programariById.values()) {
            if (!p.isAnulata()
                    && p.getMedic().equals(medic)
                    && p.getDataOra().equals(dataOra)) {
                throw new ProgramareConflictException("Medic deja programat la această oră.");
            }
        }

        Programare programare = new Programare(id, pacient, medic, dataOra);
        programariById.put(id, programare);
        return programare;
    }

    public void anuleazaProgramare(String id) {
        Programare p = programariById.get(id);
        if (p != null) {
            p.anuleaza();
        }
    }

    public List<Programare> programariDinZi(LocalDate zi) {
        List<Programare> result = new ArrayList<>();
        for (Programare p : programariById.values()) {
            if (!p.isAnulata() && p.getDataOra().toLocalDate().equals(zi)) {
                result.add(p);
            }
        }
        return result;
    }

    public List<Programare> programariPentruMedic(Medic medic) {
        List<Programare> result = new ArrayList<>();
        for (Programare p : programariById.values()) {
            if (!p.isAnulata() && p.getMedic().equals(medic)) {
                result.add(p);
            }
        }
        return result;
    }

    public Consultatie finalizeazaConsultatie(String consultatieId, String programareId,
                                              String diagnostic, String recomandari) {
        Programare p = programariById.get(programareId);
        if (p == null || p.isAnulata()) {
            return null;
        }
        Consultatie c = new Consultatie(consultatieId, p);
        c.finalizeaza(diagnostic, recomandari);
        consultatii.add(c);
        return c;
    }

    public List<Consultatie> istoricConsultatiiPacient(Pacient pacient) {
        List<Consultatie> result = new ArrayList<>();
        for (Consultatie c : consultatii) {
            if (c.getProgramare().getPacient().equals(pacient)) {
                result.add(c);
            }
        }
        return result;
    }

    public List<Programare> listeazaToateProgramarile() {
        return new ArrayList<>(programariById.values());
    }
}
