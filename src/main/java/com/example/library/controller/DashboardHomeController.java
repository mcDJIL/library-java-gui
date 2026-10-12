package com.example.library.controller;

import com.example.library.model.DataStore;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DashboardHomeController {

    @FXML private Label lblTotalBooks;
    @FXML private Label lblTotalMembers;
    @FXML private Label lblActiveTransactions;
    @FXML private Label lblTotalHistory;

    @FXML
    public void initialize() {
        lblTotalBooks.setText(String.valueOf(DataStore.books.size()));
        lblTotalMembers.setText(String.valueOf(DataStore.members.size()));
        lblActiveTransactions.setText(String.valueOf(DataStore.activeTransactions.size()));
        lblTotalHistory.setText(String.valueOf(DataStore.history.getLog().size()));
    }
}