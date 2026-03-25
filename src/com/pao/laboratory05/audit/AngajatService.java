package com.pao.laboratory05.audit;

import java.time.LocalDateTime;
import java.util.Arrays;

public class AngajatService {

    private Angajat[] angajati = new Angajat[0];
    private AuditEntry[] auditLog = new AuditEntry[0];

    private AngajatService() {}

    private static class Holder {
        private static final AngajatService INSTANCE = new AngajatService();
    }

    public static AngajatService getInstance() {
        return Holder.INSTANCE;
    }

    private void logAction(String action, String target) {
        AuditEntry entry = new AuditEntry(action, target, LocalDateTime.now().toString());

        AuditEntry[] newArray = new AuditEntry[auditLog.length + 1];
        System.arraycopy(auditLog, 0, newArray, 0, auditLog.length);
        newArray[auditLog.length] = entry;
        auditLog = newArray;
    }

    public void addAngajat(Angajat a) {
        Angajat[] newArray = new Angajat[angajati.length + 1];
        System.arraycopy(angajati, 0, newArray, 0, angajati.length);
        newArray[angajati.length] = a;
        angajati = newArray;

        System.out.println("Angajat adăugat: " + a.getNume());
        logAction("ADD", a.getNume());
    }

    public void listBySalary() {
        Angajat[] copy = angajati.clone();
        Arrays.sort(copy);

        System.out.println("--- Angajați după salariu (descrescător) ---");
        int i = 1;
        for (Angajat a : copy) {
            System.out.println(i + ". " + a);
            i++;
        }
    }

    public void findByDepartament(String numeDept) {
        logAction("FIND_BY_DEPT", numeDept);

        boolean found = false;
        System.out.println("--- Angajați din " + numeDept + " ---");

        for (Angajat a : angajati) {
            if (a.getDepartament().nume().equalsIgnoreCase(numeDept)) {
                System.out.println(a);
                found = true;
            }
        }

        if (!found) {
            System.out.println("Niciun angajat în departamentul: " + numeDept);
        }
    }

    public void printAuditLog() {
        System.out.println("--- Audit Log ---");
        for (AuditEntry e : auditLog) {
            System.out.println(e);
        }
    }
}
