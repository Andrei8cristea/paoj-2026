package com.pao.laboratory09.exercise2;

public enum TipStatus {
    PENDING(0),
    PROCESSED(1),
    REJECTED(2);

    public final int code;

    TipStatus(int code) {
        this.code = code;
    }

    public static TipStatus fromCode(int code) {
        for (TipStatus s : values()) {
            if (s.code == code) return s;
        }
        return PENDING;
    }
}
