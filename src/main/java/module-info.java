module com.example.towerdef {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires static lombok;
    requires java.desktop;

    opens com.example.towerdef to javafx.fxml;
    exports com.example.towerdef;
    exports com.example.towerdef.controller;
    opens com.example.towerdef.controller to javafx.fxml;
    exports com.example.towerdef.model.data.human.fxmlelement;
    exports com.example.towerdef.controller.scenes;
    opens com.example.towerdef.controller.scenes to javafx.fxml;

}