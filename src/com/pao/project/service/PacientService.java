package com.pao.project.service;

import com.pao.project.exception.PacientNotFoundException;
import com.pao.project.model.Pacient;

import java.util.*;

public class PacientService {

    private static PacientService instance;

    private Map<String, Pacient> pacientiById = new HashMap<>();
    private Map<String, Pacient> pacientiByCnp = new HashMap<>();

    private PacientService() {}

    public static PacientService getInstance() {
        if (instance == null) {
            instance = new PacientService();
        }
        return instance;
    }

    public void adaugaPacient(Pacient pacient) {
        if (pacient == null) return;
        pacientiById.put(pacient.getId(), pacient);
        pacientiByCnp.put(pacient.getCnp(), pacient);
    }

    public void stergePacient(String id) throws PacientNotFoundException {
        Pacient p = pacientiById.remove(id);
        if (p == null) {
            throw new PacientNotFoundException("Pacient cu id " + id + " nu a fost găsit.");
        }
        pacientiByCnp.remove(p.getCnp());
    }

    public Pacient cautaDupaId(String id) throws PacientNotFoundException {
        Pacient p = pacientiById.get(id);
        if (p == null) {
            throw new PacientNotFoundException("Pacient cu id " + id + " nu a fost găsit.");
        }
        return p;
    }

    public Pacient cautaDupaCnp(String cnp) throws PacientNotFoundException {
        Pacient p = pacientiByCnp.get(cnp);
        if (p == null) {
            throw new PacientNotFoundException("Pacient cu CNP " + cnp + " nu a fost găsit.");
        }
        return p;
    }

    public List<Pacient> listeazaToti() {
        return new ArrayList<>(pacientiById.values());
    }
}
