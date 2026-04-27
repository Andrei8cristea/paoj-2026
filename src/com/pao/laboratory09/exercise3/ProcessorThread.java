package com.pao.laboratory09.exercise3;

public class ProcessorThread implements Runnable {

    private final CoadaTranzactii banda;
    public volatile boolean activ = true;

    public ProcessorThread(CoadaTranzactii banda) {
        this.banda = banda;
    }

    @Override
    public void run() {
        try {
            while (activ) {
                Tranzactie t = banda.extrage();
                System.out.println("[Processor] Factura #" + t.getId() +
                        " - " + t.getSuma() + " RON | " + t.getData());
                Thread.sleep(80);
            }
        } catch (InterruptedException e) {
            // se oprește grațios
        }
    }
}
