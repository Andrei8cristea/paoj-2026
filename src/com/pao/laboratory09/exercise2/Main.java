package com.pao.laboratory09.exercise2;

import com.pao.laboratory09.exercise1.TipTranzactie;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.*;

public class Main {

    private static final String FILE_PATH = "output/lab09_ex2.bin";
    private static final int RECORD_SIZE = 32;

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int N = Integer.parseInt(sc.nextLine());

        // Asigurăm folderul output
        File dir = new File("output");
        if (!dir.exists()) dir.mkdirs();

        // Scriere inițială în fișier
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(FILE_PATH))) {

            for (int i = 0; i < N; i++) {

                int id = sc.nextInt();
                double suma = sc.nextDouble();
                String data = sc.next();
                String tipStr = sc.next();

                TipTranzactie tip = TipTranzactie.valueOf(tipStr);

                // id (4 bytes LE)
                byte[] idBytes = ByteBuffer.allocate(4)
                        .order(ByteOrder.LITTLE_ENDIAN)
                        .putInt(id)
                        .array();
                dos.write(idBytes);

                // suma (8 bytes LE)
                byte[] sumaBytes = ByteBuffer.allocate(8)
                        .order(ByteOrder.LITTLE_ENDIAN)
                        .putDouble(suma)
                        .array();
                dos.write(sumaBytes);

                // data (10 bytes ASCII + padding)
                byte[] dataBytes = data.getBytes();
                dos.write(dataBytes);
                for (int k = dataBytes.length; k < 10; k++) {
                    dos.writeByte(' ');
                }

                // tip (1 byte)
                dos.writeByte(tip == TipTranzactie.CREDIT ? 0 : 1);

                // status (1 byte) — inițial PENDING
                dos.writeByte(0);

                // padding final (8 bytes zero)
                for (int k = 0; k < 8; k++) dos.writeByte(0);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        sc.nextLine(); // consumă newline

        // Procesare comenzi
        while (sc.hasNextLine()) {
            String line = sc.nextLine().trim();
            if (line.isEmpty()) continue;

            String[] parts = line.split(" ");

            switch (parts[0]) {

                case "READ": {
                    int idx = Integer.parseInt(parts[1]);
                    readRecord(idx);
                    break;
                }

                case "UPDATE": {
                    int idx = Integer.parseInt(parts[1]);
                    String statusStr = parts[2];

                    TipStatus status = TipStatus.valueOf(statusStr);

                    updateStatus(idx, status);

                    System.out.println("Updated [" + idx + "]: " + statusStr);
                    break;
                }

                case "PRINT_ALL": {
                    for (int i = 0; i < N; i++) {
                        readRecord(i);
                    }
                    break;
                }
            }
        }
    }

    private static void readRecord(int idx) {
        try (RandomAccessFile raf = new RandomAccessFile(FILE_PATH, "r")) {

            raf.seek(idx * RECORD_SIZE);

            byte[] buffer = new byte[RECORD_SIZE];
            raf.readFully(buffer);

            ByteBuffer bb = ByteBuffer.wrap(buffer).order(ByteOrder.LITTLE_ENDIAN);

            int id = bb.getInt();
            double suma = bb.getDouble();

            byte[] dataBytes = new byte[10];
            bb.get(dataBytes);
            String data = new String(dataBytes).trim();

            int tipCode = bb.get();
            int statusCode = bb.get();

            TipTranzactie tip = (tipCode == 0 ? TipTranzactie.CREDIT : TipTranzactie.DEBIT);
            TipStatus status = TipStatus.fromCode(statusCode);

            System.out.printf(
                    "[%d] id=%d data=%s tip=%s suma=%.2f RON status=%s%n",
                    idx, id, data, tip, suma, status
            );

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void updateStatus(int idx, TipStatus status) {
        try (RandomAccessFile raf = new RandomAccessFile(FILE_PATH, "rw")) {

            long pos = idx * RECORD_SIZE + 23; // offset status
            raf.seek(pos);
            raf.writeByte(status.code);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
