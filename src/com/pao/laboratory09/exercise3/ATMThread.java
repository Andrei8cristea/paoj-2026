package com.pao.laboratory09.exercise3;

public class ATMThread extends Thread {

    private final int atmId;
    private final CoadaTranzactii banda;

    public ATMThread(int atmId, CoadaTranzactii banda) {
        this.atmId = atmId;
        this.banda = banda;
    }

    @Override
    public void run() {
        try {
            for (int i = 1; i <= 4; i++) {
                Tranzactie t = new Tranzactie(
                        atmId * 100 + i,
                        100 + Math.random() * 900,
                        "2024-01-01"
                );

                banda.adauga(t, atmId);
                System.out.println("[ATM-" + atmId + "] trimite: " + t);

                Thread.sleep(50);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
