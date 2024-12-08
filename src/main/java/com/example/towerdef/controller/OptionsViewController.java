package com.example.towerdef.controller;

import com.example.towerdef.controller.scenes.SceneController;
import com.example.towerdef.controller.scenes.SceneNames;
import com.example.towerdef.model.data.human.HumanUnitName;
import com.example.towerdef.model.data.weapon.WeaponName;
import com.example.towerdef.model.gamelogic.runtime.Validator;
import com.example.towerdef.model.gamelogic.setup.GameSettings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;

import java.util.function.UnaryOperator;


public class OptionsViewController {

    @FXML
    protected ComboBox<String> humanPos1, humanPos2, humanPos3;

    @FXML
    protected ComboBox<WeaponName> towerWeaponComboBox;

    @FXML
    protected Slider towerHealthSlider, humanHealthSlider;

    @FXML
    protected TextField humanHealthText, towerHealthText;

    @FXML
    protected Label towerWeaponLabel;

    private UnaryOperator<TextFormatter.Change> filter;

    private final int minHumanHealth = 50;
    private final int maxHumanHealth = 500;
    private final int minTowerHealth = 500;
    private final int maxTowerHealth = 5000;

    private Validator validator;

    public OptionsViewController() {
        validator = new Validator();
    }

    public void initialize() {
        initComboBox();
        initSliders();
        initFilter();
        initTextFields();
    }

    private void initComboBox() {
        GameSettings.removeInstance();
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

        towerWeaponComboBox.setItems(FXCollections.observableArrayList(
                WeaponName.LASER,
                WeaponName.MINIGUN
        ));
        towerWeaponComboBox.setValue(WeaponName.MINIGUN);
    }

    private void initFilter() {
        filter = change -> {
            String text = change.getText();
            if (!text.matches("[a-zA-Z]")) {
                return change;
            }
            return null;
        };
    }

    private void initTextFields() {
        humanHealthText.setTextFormatter(new TextFormatter<>(filter));
        humanHealthText.textProperty().addListener(
                ((observableValue, oldValue, newValue) ->
                        setHealth(humanHealthSlider.getId(), newValue))
        );

        towerHealthText.setTextFormatter(new TextFormatter<>(filter));
        towerHealthText.textProperty().addListener(
                ((observableValue, oldValue, newValue) ->
                        setHealth(towerHealthSlider.getId(), newValue))
        );
        humanHealthText.focusedProperty().addListener((observable, wasFocused, isNowFocused) -> {
            checkHumanHealthText(isNowFocused);
        });
        towerHealthText.focusedProperty().addListener((observable, wasFocused, isNowFocused) -> {
            checkTowerHealthText(isNowFocused);
        });
        humanHealthText.setText(String.valueOf((int) humanHealthSlider.getValue()));
        towerHealthText.setText(String.valueOf((int) towerHealthSlider.getValue()));
    }

    private void initSliders() {
        humanHealthSlider.setMin(minHumanHealth);
        humanHealthSlider.setMax(maxHumanHealth);
        humanHealthSlider.setShowTickLabels(true);
        humanHealthSlider.setShowTickMarks(true);
        humanHealthSlider.setMajorTickUnit(minHumanHealth);
        humanHealthSlider.setBlockIncrement(minHumanHealth);
        humanHealthSlider.setValue(100);

        towerHealthSlider.setMin(minTowerHealth);
        towerHealthSlider.setMax(maxTowerHealth);
        towerHealthSlider.setShowTickLabels(true);
        towerHealthSlider.setShowTickMarks(true);
        towerHealthSlider.setMajorTickUnit(minTowerHealth);
        towerHealthSlider.setBlockIncrement(minTowerHealth);
        towerHealthSlider.setValue(1500);
    }

    @FXML
    public void backToStart() {
        SceneController sceneController = SceneController.getInstance();
        GameSettings.getInstance(
                (int) humanHealthSlider.getValue(),
                (int) towerHealthSlider.getValue(),
                getHumanUnitNames(),
                towerWeaponComboBox.getValue());
        sceneController.activate(SceneNames.MAIN);
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
        setHealth(slider.getId(), String.valueOf((int) slider.getValue()));
    }

    @FXML
    public void changeTowerWeapon(){

    }

    public void checkHumanHealthText(boolean isFocused) {
        if(!isFocused){
            setHealth(humanHealthSlider.getId(),
                    validator.getInValueInLimits(humanHealthText.getText(), minHumanHealth, maxHumanHealth));

        }
    }
    public void checkTowerHealthText(boolean isFocused) {
        if(!isFocused){
            setHealth(towerHealthSlider.getId(),
                    validator.getInValueInLimits(towerHealthText.getText(), minTowerHealth, maxTowerHealth));
        }
    }

    private void setHealth(String id, String value) {
        if (value.matches("\\d?")) return;
        if (value.isEmpty()) return;
        int valueInt = (int) Double.parseDouble(value);
        if (id.equals(towerHealthSlider.getId())) {
            towerHealthText.setText(value);
            towerHealthSlider.setValue(valueInt);
        } else {
            humanHealthText.setText(value);
            humanHealthSlider.setValue(valueInt);
        }
    }

    private void unfocusedTextField(String id, boolean unfocused){
        if(id.equals(humanHealthText.getId())){
            humanHealthText.getText();
        }
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

}
