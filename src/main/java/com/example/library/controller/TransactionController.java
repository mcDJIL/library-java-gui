package com.example.library.controller;

import com.example.library.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.util.StringConverter;
import java.time.LocalDate;

public class TransactionController {
    @FXML private ComboBox<Member> memberComboBox;
    @FXML private ComboBox<Book> bookComboBox;
    @FXML private ComboBox<Transaction> activeTransactionComboBox;
    @FXML private Label statusLabel;
    @FXML private Label returnStatusLabel;

    private int trxCounter = 1;

    @FXML
    public void initialize() {
        memberComboBox.setItems(DataStore.members);
        bookComboBox.setItems(DataStore.books);
        activeTransactionComboBox.setItems(DataStore.activeTransactions);

        bookComboBox.setConverter(new StringConverter<>() {
            @Override public String toString(Book b) { return b != null ? b.getTitle() + " (Stok: " + b.getStock() + ")" : ""; }
            @Override public Book fromString(String string) { return null; }
        });

        activeTransactionComboBox.setConverter(new StringConverter<>() {
            @Override public String toString(Transaction t) {
                return t != null ? t.getTransactionId() + " - " + t.getMember().getName() + " (" + t.getBook().getTitle() + ")" : "";
            }
            @Override public Transaction fromString(String string) { return null; }
        });
    }

    @FXML
    void handleBorrow(ActionEvent event) {
        Member m = memberComboBox.getValue();
        Book b = bookComboBox.getValue();

        if (m == null || b == null) {
            showStatus(statusLabel, "Pilih anggota dan buku!", false);
            return;
        }

        String trxId = "TRX-" + String.format("%03d", trxCounter++);
        Transaction trx = new Transaction(trxId, m, b, LocalDate.now());

        if (trx.processBorrow()) {
            DataStore.history.addRecord(trx);
            DataStore.activeTransactions.add(trx);
            showStatus(statusLabel, "Sukses meminjam: " + b.getTitle(), true);
            refreshComboBoxes();
        } else {
            if (!m.canBorrow()) showStatus(statusLabel, "Gagal: Limit pinjaman tercapai!", false);
            else if (b.getStock() <= 0) showStatus(statusLabel, "Gagal: Stok buku habis!", false);
        }
    }

    @FXML
    void handleReturn(ActionEvent event) {
        Transaction trx = activeTransactionComboBox.getValue();
        if (trx == null) {
            showStatus(returnStatusLabel, "Pilih transaksi yang ingin dikembalikan!", false);
            return;
        }

        if (trx.processReturn()) {
            DataStore.activeTransactions.remove(trx);
            showStatus(returnStatusLabel, "Buku berhasil dikembalikan!", true);
            refreshComboBoxes();
        }
    }

    private void showStatus(Label label, String msg, boolean isSuccess) {
        label.setText(msg);
        label.setStyle(isSuccess ? "-fx-text-fill: #10b981;" : "-fx-text-fill: #ef4444;");
    }

    private void refreshComboBoxes() {
        // Trik kecil untuk merender ulang isi ComboBox agar stok/limit terbaru langsung tampil
        bookComboBox.setItems(null);
        bookComboBox.setItems(DataStore.books);
        memberComboBox.setItems(null);
        memberComboBox.setItems(DataStore.members);
    }
}