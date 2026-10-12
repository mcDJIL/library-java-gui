module com.example.library {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;

    opens com.example.library to javafx.fxml;
    exports com.example.library;

    opens com.example.library.controller to javafx.fxml;
    opens com.example.library.model to javafx.base;

    exports com.example.library.controller;
    exports com.example.library.model;
}