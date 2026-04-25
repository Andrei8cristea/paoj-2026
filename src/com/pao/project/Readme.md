# Sistem de Management — Cabinet Medical

## 1.1 — Lista cu cel puțin 10 acțiuni / interogări ale sistemului

1. Înregistrează un pacient nou în sistem
2. Adaugă un medic nou în cabinet
3. Programează o consultație pentru un pacient
4. Anulează o programare
5. Caută pacient după CNP
6. Caută medic după specializare
7. Listează toate programările dintr-o anumită zi
8. Marchează o consultație ca finalizată
9. Afișează istoricul consultațiilor unui pacient
10. Listează toți medicii ordonați alfabetic după nume
11. Afișează programările unui medic
12. Elimină un pacient din sistem (cu validări)

## 1.2 — Lista cu cel puțin 8 tipuri de obiecte din domeniu

1. **Persoana** — clasă abstractă, baza pentru Pacient și Medic
2. **Pacient** — pacient înregistrat în cabinet
3. **Medic** — medic care oferă consultații
4. **MedicSpecialist** — medic cu competență suplimentară și experiență (extinde Medic)
5. **Specializare** — enum cu tipurile de specializare medicală
6. **Programare** — programare a unui pacient la un medic
7. **Consultatie** — consultație realizată pe baza unei programări
8. **RaportMedical** — raport imutabil generat la finalizarea consultației
9. **CabinetMedical** — cabinetul medical (agregator, conține lista de medici)

## Structura proiectului

```
src/
└── com/pao/project/
    ├── model/
    │   ├── Persoana.java          (clasă abstractă)
    │   ├── Pacient.java           (extends Persoana)
    │   ├── Medic.java             (extends Persoana, implements Comparable)
    │   ├── MedicSpecialist.java   (extends Medic — nivel 2 moștenire)
    │   ├── Specializare.java      (enum)
    │   ├── Programare.java
    │   ├── Consultatie.java
    │   ├── RaportMedical.java     (clasă imutabilă)
    │   └── CabinetMedical.java
    │
    ├── service/
    │   ├── PacientService.java    (Singleton)
    │   ├── MedicService.java      (Singleton)
    │   └── ProgramareService.java (Singleton)
    │
    ├── exception/
    │   ├── PacientNotFoundException.java
    │   ├── MedicNotFoundException.java
    │   └── ProgramareConflictException.java
    │
    └── Main.java
```

## Ierarhia de moștenire

```
Persoana (abstractă)
├── Pacient
└── Medic (implements Comparable<Medic>)
    └── MedicSpecialist
```