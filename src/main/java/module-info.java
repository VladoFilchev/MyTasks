module com.example.tasksmanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires eu.hansolo.fx.countries;
    requires eu.hansolo.toolboxfx;
    requires eu.hansolo.fx.heatmap;
    requires eu.hansolo.toolbox;
    requires java.sql;


    opens com.example.tasksmanagementsystem.controller to javafx.fxml; // Add this line
    exports com.example.tasksmanagementsystem.controller;
    opens com.example.tasksmanagementsystem to javafx.fxml;
    exports com.example.tasksmanagementsystem;
    exports com.example.tasksmanagementsystem.object;
    opens com.example.tasksmanagementsystem.object to javafx.fxml;
    exports com.example.tasksmanagementsystem.enums;
}