package com.pao.project.model;

import java.time.LocalDateTime;

public final class RaportMedical {
    private final String id;
    private final String pacientId;
    private final String medicId;
    private final String diagnostic;
    private final String recomandari;
    private final LocalDateTime data;

    public RaportMedical(String id, String pacientId, String medicId,
                         String diagnostic, String recomandari, LocalDateTime data) {
        this.id = id;
        this.pacientId = pacientId;
        this.medicId = medicId;
        this.diagnostic = diagnostic;
        this.recomandari = recomandari;
        this.data = data;
    }

    public String getId() { return id; }
    public String getPacientId() { return pacientId; }
    public String getMedicId() { return medicId; }
    public String getDiagnostic() { return diagnostic; }
    public String getRecomandari() { return recomandari; }
    public LocalDateTime getData() { return data; }

    @Override
    public String toString() {
        return "RaportMedical{" +
                "id='" + id + '\'' +
                ", pacientId='" + pacientId + '\'' +
                ", medicId='" + medicId + '\'' +
                ", diagnostic='" + diagnostic + '\'' +
                ", recomandari='" + recomandari + '\'' +
                ", data=" + data +
                '}';
    }
}
