package com.example.library.controller;

import com.example.library.model.DataStore;
import com.example.library.model.Transaction;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class HistoryController {

    @FXML private TableView<Transaction> historyTable;
    @FXML private TableColumn<Transaction, String> colTrxId;
    @FXML private TableColumn<Transaction, String> colMember;
    @FXML private TableColumn<Transaction, String> colBook;
    @FXML private TableColumn<Transaction, String> colStatus;

    @FXML
    public void initialize() {
        // Mengubah List biasa dari model History menjadi ObservableList agar bisa dibaca TableView
        ObservableList<Transaction> logData = FXCollections.observableArrayList(DataStore.history.getLog());

        // Mapping Kolom
        colTrxId.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getTransactionId()));

        colMember.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getMember().getName()));

        colBook.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getBook().getTitle()));

        colStatus.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().isReturned() ? "Dikembalikan" : "Dipinjam"));

        historyTable.setItems(logData);
    }
}