package com.example.library.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

public class DashboardController {

    @FXML private StackPane contentArea;

    @FXML
    public void initialize() {
        loadView("DashboardHome.fxml");
    }

    @FXML void showHome(ActionEvent event) { loadView("DashboardHome.fxml"); }
    @FXML void showTransactions(ActionEvent event) { loadView("TransactionView.fxml"); }
    @FXML void showBooks(ActionEvent event) { loadView("BookView.fxml"); }
    @FXML void showMembers(ActionEvent event) { loadView("MemberView.fxml"); }
    @FXML void showHistory(ActionEvent event) { loadView("HistoryView.fxml"); }

    private void loadView(String fxmlFileName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/library/view/" + fxmlFileName));
            Node node = loader.load();
            contentArea.getChildren().setAll(node);
        } catch (Exception e) {
            System.err.println("Gagal meload: " + fxmlFileName);
            e.printStackTrace();
        }
    }
}