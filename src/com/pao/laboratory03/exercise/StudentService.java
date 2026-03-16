package com.pao.laboratory03.exercise;

import java.util.*;

public class StudentService {

    private static StudentService instance;
    private final List<Student> students = new ArrayList<>();

    private StudentService() {}

    public static StudentService getInstance() {
        if (instance == null) instance = new StudentService();
        return instance;
    }

    public void addStudent(String name, int age) {
        for (Student s : students) {
            if (s.getName().equalsIgnoreCase(name)) {
                throw new RuntimeException("Studentul '" + name + "' există deja!");
            }
        }
        students.add(new Student(name, age));
    }

    public Student findByName(String name) {
        return students.stream()
                .filter(s -> s.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new StudentNotFoundException("Studentul '" + name + "' nu există!"));
    }

    public void addGrade(String studentName, Subject subject, double grade) {
        Student s = findByName(studentName);
        s.addGrade(subject, grade);
    }

    public void printAllStudents() {
        students.forEach(s -> {
            System.out.println(s);
            s.getGrades().forEach((subj, gr) ->
                    System.out.println("   " + subj + " → " + gr));
        });
    }

    public void printTopStudents() {
        students.stream()
                .sorted((a, b) -> Double.compare(b.getAverage(), a.getAverage()))
                .forEach(System.out::println);
    }

    public Map<Subject, Double> getAveragePerSubject() {
        Map<Subject, List<Double>> map = new HashMap<>();

        for (Student s : students) {
            for (var entry : s.getGrades().entrySet()) {
                map.putIfAbsent(entry.getKey(), new ArrayList<>());
                map.get(entry.getKey()).add(entry.getValue());
            }
        }

        Map<Subject, Double> result = new HashMap<>();
        for (var e : map.entrySet()) {
            double avg = e.getValue().stream().mapToDouble(Double::doubleValue).average().orElse(0);
            result.put(e.getKey(), avg);
        }

        return result;
    }
}