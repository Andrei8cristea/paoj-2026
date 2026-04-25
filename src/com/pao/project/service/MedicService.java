package com.pao.project.service;

import com.pao.project.exception.MedicNotFoundException;
import com.pao.project.model.Medic;
import com.pao.project.model.Specializare;

import java.util.*;

public class MedicService {

    private static MedicService instance;

    private Map<String, Medic> mediciById = new HashMap<>();
    private Set<Medic> mediciSortati = new TreeSet<>();

    private MedicService() {}

    public static MedicService getInstance() {
        if (instance == null) {
            instance = new MedicService();
        }
        return instance;
    }

    public void adaugaMedic(Medic medic) {
        if (medic == null) return;
        mediciById.put(medic.getId(), medic);
        mediciSortati.add(medic);
    }

    public void stergeMedic(String id) throws MedicNotFoundException {
        Medic m = mediciById.remove(id);
        if (m == null) {
            throw new MedicNotFoundException("Medic cu id " + id + " nu a fost găsit.");
        }
        mediciSortati.remove(m);
    }

    public Medic cautaDupaId(String id) throws MedicNotFoundException {
        Medic m = mediciById.get(id);
        if (m == null) {
            throw new MedicNotFoundException("Medic cu id " + id + " nu a fost găsit.");
        }
        return m;
    }

    public List<Medic> cautaDupaSpecializare(Specializare specializare) {
        List<Medic> result = new ArrayList<>();
        for (Medic m : mediciById.values()) {
            if (m.getSpecializare() == specializare) {
                result.add(m);
            }
        }
        return result;
    }

    public List<Medic> listeazaToti() {
        return new ArrayList<>(mediciById.values());
    }

    public List<Medic> listeazaSortatAlfabetic() {
        return new ArrayList<>(mediciSortati);
    }
}
