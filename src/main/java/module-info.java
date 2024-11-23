module com.example.towerdef {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires static lombok;

    opens com.example.towerdef to javafx.fxml;
    exports com.example.towerdef;
    exports com.example.towerdef.controller;
    opens com.example.towerdef.controller to javafx.fxml;
    exports com.example.towerdef.model;
    opens com.example.towerdef.model to javafx.fxml;
}