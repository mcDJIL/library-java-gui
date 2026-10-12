package com.example.library.controller;

import com.example.library.model.Book;
import com.example.library.model.DataStore;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class BookController {
    @FXML private TableView<Book> bookTable;
    @FXML private TableColumn<Book, String> colId, colTitle;
    @FXML private TableColumn<Book, Integer> colStock;

    @FXML private TextField txtTitle;
    @FXML private TextField txtStock;

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colStock.setCellValueFactory(new PropertyValueFactory<>("stock"));

        bookTable.setItems(DataStore.books);
    }

    @FXML
    void handleAddBook(ActionEvent event) {
        String title = txtTitle.getText();
        String stockStr = txtStock.getText();

        if (title.isEmpty() || stockStr.isEmpty()) {
            showAlert("Input Tidak Valid", "Judul dan Stok tidak boleh kosong!");
            return;
        }

        try {
            int stock = Integer.parseInt(stockStr);
            if (stock < 0) throw new NumberFormatException();

            String newId = "B" + String.format("%03d", DataStore.books.size() + 1);

            DataStore.books.add(new Book(newId, title, stock));

            txtTitle.clear();
            txtStock.clear();
        } catch (NumberFormatException e) {
            showAlert("Input Tidak Valid", "Stok harus berupa angka positif!");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}