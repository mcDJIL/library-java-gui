package com.example.library.model;

import java.util.ArrayList;
import java.util.List;

public class History {
    private List<Transaction> log;

    public History() {
        this.log = new ArrayList<>();
    }

    public void addRecord(Transaction transaction) {
        log.add(transaction);
    }

    public void printHistory() {
        System.out.println("\n=== RIWAYAT TRANSAKSI ===");
        if (log.isEmpty()) {
            System.out.println("Belum ada transaksi yang tercatat.");
            return;
        }
        for (Transaction t : log) {
            System.out.println(t);
        }
    }

    public List<Transaction> getLog() {
        return log;
    }
}