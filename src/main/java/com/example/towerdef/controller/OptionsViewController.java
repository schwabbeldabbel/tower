package com.example.towerdef.controller;

import com.example.towerdef.controller.scenes.SceneController;
import com.example.towerdef.controller.scenes.SceneNames;
import com.example.towerdef.model.data.human.HumanUnitName;
import com.example.towerdef.model.data.weapon.WeaponName;
import com.example.towerdef.model.gamelogic.setup.GameSettings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.StackPane;


public class OptionsViewController {

    @FXML
    protected ComboBox<String> humanPos1, humanPos2, humanPos3;

    @FXML
    protected Slider towerHealthSlider, humanHealthSlider;

    @FXML
    protected Label humanHealthLabel, towerHealthLabel;

    public void initialize() {
        initComboBox();
        initSliders();
    }

    private void initComboBox() {
        ObservableList<String> humanClasses = FXCollections.observableArrayList(
                HumanUnitName.ENGINEER.getName(),
                HumanUnitName.TANK.getName(),
                HumanUnitName.SNIPER.getName(),
                HumanUnitName.NONE.getName()
        );
        humanPos1.setItems(humanClasses);
        humanPos1.setValue(HumanUnitName.NONE.getName());
        humanPos2.setItems(humanClasses);
        humanPos2.setValue(HumanUnitName.NONE.getName());
        humanPos3.setItems(humanClasses);
        humanPos3.setValue(HumanUnitName.NONE.getName());

    }

    private void initSliders() {
        humanHealthSlider.setMin(50);
        humanHealthSlider.setMax(500);
        towerHealthSlider.setMin(500);
        towerHealthSlider.setMax(5000);
        humanHealthSlider.setShowTickLabels(true);
        towerHealthSlider.setShowTickLabels(true);
        humanHealthSlider.setShowTickMarks(true);
        towerHealthSlider.setShowTickMarks(true);
        towerHealthSlider.setMajorTickUnit(500f);
        humanHealthSlider.setMajorTickUnit(50f);
        humanHealthSlider.setBlockIncrement(50f);

        humanHealthSlider.setValue(100);
        towerHealthSlider.setValue(1500);
        humanHealthLabel.setText("Aktuell: " + (int) humanHealthSlider.getValue());
        towerHealthLabel.setText("Aktuell: " + (int) towerHealthSlider.getValue());

    }

    @FXML
    public void backToStart() {
        SceneController sceneController = SceneController.getInstance();
        GameSettings.getInstance(
                (int) humanHealthSlider.getValue(),
                (int) towerHealthSlider.getValue(),
                getHumanUnitNames(),
                WeaponName.LASER);
        sceneController.activate(SceneNames.MAIN);
    }

    private HumanUnitName[] getHumanUnitNames() {
        return new HumanUnitName[]{
                getHumanUnitName(humanPos1.getValue()),
                getHumanUnitName(humanPos2.getValue()),
                getHumanUnitName(humanPos3.getValue())
        };
    }

    private HumanUnitName getHumanUnitName(String humanName) {
        if (humanName.equals(HumanUnitName.TANK.getName())) {
            return HumanUnitName.TANK;
        } else if (humanName.equals(HumanUnitName.SNIPER.getName())) {
            return HumanUnitName.SNIPER;
        } else if (humanName.equals(HumanUnitName.ENGINEER.getName())) {
            return HumanUnitName.ENGINEER;
        }
        return HumanUnitName.NONE;
    }

    @FXML
    public void setHuman(Event event) {
        ComboBox<String> clickedBox = (ComboBox<String>) event.getSource();
        StackPane parent = (StackPane) clickedBox.getParent();
        parent.getStyleClass().setAll(getHumanUnitName(clickedBox.getValue()).getCss());
    }

    @FXML
    public void changeHealth(Event event) {
        Slider slider = (Slider) event.getSource();
        if (slider.getId().equals("towerHealthSlider")) {
            towerHealthLabel.setText("Aktuell: " + (int) slider.getValue());
        } else {
            humanHealthLabel.setText("Aktuell: " + (int) slider.getValue());
        }
    }

}
