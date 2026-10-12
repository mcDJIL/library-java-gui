package com.example.library.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DataStore {
    // Menggunakan ObservableList agar UI otomatis update jika data berubah
    public static ObservableList<Book> books = FXCollections.observableArrayList();
    public static ObservableList<Member> members = FXCollections.observableArrayList();
    public static History history = new History();
    public static ObservableList<Transaction> activeTransactions = FXCollections.observableArrayList();

    static {
        // Data Dummy
        books.addAll(
                new Book("B001", "Pemrograman Java Canggih", 5),
                new Book("B002", "Algoritma & Struktur Data", 2),
                new Book("B003", "Desain UI/UX dengan Figma", 1),
                new Book("B004", "Machine Learning Python", 3)
        );

        members.addAll(
                new Member("M001", "Moch Djauharil Ilmi"),
                new Member("M002", "Mas Kiki"),
                new Member("M003", "Budi Santoso")
        );
    }
}