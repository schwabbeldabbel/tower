module com.example.towerdef {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires static lombok;
    requires java.desktop;
    requires com.jfoenix;

    opens com.example.towerdef to javafx.fxml;
    exports com.example.towerdef;
    exports com.example.towerdef.controller;
    exports com.example.towerdef.controller.scenes;
    opens com.example.towerdef.controller to javafx.fxml;
    exports com.example.towerdef.model.data.human;
    exports com.example.towerdef.model.data.tower;
    exports com.example.towerdef.model.data.weapon;
    exports com.example.towerdef.model.data.weapon.fxmlelement;
    opens com.example.towerdef.controller.scenes to javafx.fxml;
    exports com.example.towerdef.model.gamelogic.review;
    exports com.example.towerdef.model.gamelogic.runtime;
    exports com.example.towerdef.model.gamelogic.setup;
    exports com.example.towerdef.model.gamelogic.runtime.time;
    exports com.example.towerdef.model.services;
}