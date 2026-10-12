package com.example.library.controller;

import com.example.library.model.DataStore;
import com.example.library.model.Member;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class MemberController {
    @FXML private TableView<Member> memberTable;
    @FXML private TableColumn<Member, String> colId, colName;
    @FXML private TableColumn<Member, Integer> colBorrowed;

    @FXML private TextField txtName;

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("memberId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colBorrowed.setCellValueFactory(new PropertyValueFactory<>("borrowedCount"));

        memberTable.setItems(DataStore.members);
    }

    @FXML
    void handleAddMember(ActionEvent event) {
        String name = txtName.getText();

        if (name.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Input Tidak Valid");
            alert.setHeaderText(null);
            alert.setContentText("Nama anggota tidak boleh kosong!");
            alert.showAndWait();
            return;
        }

        String newId = "M" + String.format("%03d", DataStore.members.size() + 1);

        DataStore.members.add(new Member(newId, name));
        txtName.clear();
    }
}