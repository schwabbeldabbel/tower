package com.example.towerdef.controller;

import com.example.towerdef.controller.scenes.SceneController;
import com.example.towerdef.controller.scenes.SceneNames;
import com.example.towerdef.model.data.human.HumanUnit;
import com.example.towerdef.model.data.human.HumanUnitName;
import com.example.towerdef.model.data.tower.Tower;
import com.example.towerdef.model.data.weapon.WeaponName;
import com.example.towerdef.model.gamelogic.runtime.Validator;
import com.example.towerdef.model.gamelogic.setup.GameSettings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;

import java.util.List;
import java.util.function.UnaryOperator;

import static com.example.towerdef.model.data.weapon.WeaponName.*;


public class OptionsViewController {

    private final int minHumanHealth = 50;
    private final int maxHumanHealth = 500;
    private final int minTowerHealth = 500;
    private final int maxTowerHealth = 5000;
    @FXML
    protected ComboBox<String> humanPos1, humanPos2, humanPos3;
    @FXML
    protected ComboBox<String> towerWeaponComboBox;
    @FXML
    protected Slider towerHealthSlider, humanHealthSlider;
    @FXML
    protected TextField humanHealthText, towerHealthText;
    @FXML
    protected Label humanLabel1, humanLabel2, humanLabel3;
    private UnaryOperator<TextFormatter.Change> filter;
    private Validator validator;

    private GameSettings gameSettings;
    private List<HumanUnit> humanUnits;
    private Tower tower;

    public OptionsViewController() {
        validator = new Validator();
    }

    public void initialize() {
        this.gameSettings = GameSettings.getInstance();
        this.humanUnits = gameSettings.getNewHumanUnits();
        this.tower = gameSettings.getNewTower();
        initHumanNames();
        initComboBox();
        initSliders();
        initFilter();
        initTextFields();
    }

    private void initHumanNames(){
        humanLabel1.setText(HumanUnitName.ENGINEER.getName());
        humanLabel2.setText(HumanUnitName.TANK.getName());
        humanLabel3.setText(HumanUnitName.SNIPER.getName());
    }

    private void initComboBox() {
        ObservableList<String> humanClasses = FXCollections.observableArrayList(
                HumanUnitName.ENGINEER.getName(),
                HumanUnitName.TANK.getName(),
                HumanUnitName.SNIPER.getName(),
                HumanUnitName.NONE.getName()
        );
        humanPos1.setItems(humanClasses);
        humanPos2.setItems(humanClasses);
        humanPos3.setItems(humanClasses);
        towerWeaponComboBox.setItems(FXCollections.observableArrayList(
                LASER.getName(),
                MINIGUN.getName(),
                HANDGUN.getName()
        ));
        humanUnits.forEach(human -> {
            if(human.getPosition() == 0){
                humanPos1.setValue(human.getName().getName());
                setHumansClass(humanPos1);
            }else if(human.getPosition() == 1){
                humanPos2.setValue(humanUnits.get(1).getName().getName());
                setHumansClass(humanPos2);
            }else if(human.getPosition() == 2){
                humanPos3.setValue(humanUnits.get(2).getName().getName());
                setHumansClass(humanPos3);
            }
        });
        if(humanPos1.getValue() == null){
            humanPos1.setValue(HumanUnitName.NONE.getName());
            setHumansClass(humanPos1);
        }
        if(humanPos2.getValue() == null){
            humanPos2.setValue(HumanUnitName.NONE.getName());
            setHumansClass(humanPos2);
        }
        if(humanPos3.getValue() == null){
            humanPos3.setValue(HumanUnitName.NONE.getName());
            setHumansClass(humanPos3);
        }
        towerWeaponComboBox.setValue(gameSettings.getTower().getWeapon().getName().getName());
    }

    private void initFilter() {
        filter = change -> {
            String text = change.getText();
            if (!text.matches("[a-zA-Z]") && !text.equals(" ") && !text.matches(".*[^a-zA-Z0-9\\s].*")) {
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
        humanHealthSlider.setValue(gameSettings.getBaseHealthHuman());

        towerHealthSlider.setMin(minTowerHealth);
        towerHealthSlider.setMax(maxTowerHealth);
        towerHealthSlider.setShowTickLabels(true);
        towerHealthSlider.setShowTickMarks(true);
        towerHealthSlider.setMajorTickUnit(minTowerHealth);
        towerHealthSlider.setBlockIncrement(minTowerHealth);
        towerHealthSlider.setValue(gameSettings.getBaseHealthTower());
    }

    @FXML
    public void backToStart() {
        SceneController sceneController = SceneController.getInstance();
        setGameSettings();
        sceneController.activate(SceneNames.MAIN);
    }

    @FXML
    public void startGame() {
        SceneController sceneController = SceneController.getInstance();
        setGameSettings();
        sceneController.activate(SceneNames.GAME);
    }

    @FXML
    public void resetOptions(){
        GameSettings.removeInstance();
        initialize();
    }

    @FXML
    public void setHuman(Event event) {
        ComboBox<String> clickedBox = (ComboBox<String>) event.getSource();
        setHumansClass(clickedBox);
    }

    @FXML
    public void changeHealth(Event event) {
        Slider slider = (Slider) event.getSource();
        setHealth(slider.getId(), String.valueOf((int) slider.getValue()));
    }

    @FXML
    public void unfocusedTextFields() {
        towerHealthText.getParent().requestFocus();
    }

    public void checkHumanHealthText(boolean isFocused) {
        if (!isFocused) {
            setHealth(humanHealthSlider.getId(),
                    validator.getInValueInLimits(humanHealthText.getText(), minHumanHealth, maxHumanHealth));

        }
    }

    public void checkTowerHealthText(boolean isFocused) {
        if (!isFocused) {
            setHealth(towerHealthSlider.getId(),
                    validator.getInValueInLimits(towerHealthText.getText(), minTowerHealth, maxTowerHealth));
        }
    }

    private void setHumansClass(ComboBox<String> comboBox) {
        StackPane parent = (StackPane) comboBox.getParent();
        parent.getStyleClass().setAll(getHumanUnitName(comboBox.getValue()).getCss());
        parent.getStyleClass().add("charakter");
    }

    private void setGameSettings() {
        GameSettings.removeInstance();
        GameSettings.getInstance(
                (int) humanHealthSlider.getValue(),
                (int) towerHealthSlider.getValue(),
                getHumanUnitNames(),
                getTowerWeapon());
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

    private HumanUnitName[] getHumanUnitNames() {
        return new HumanUnitName[]{
                getHumanUnitName(humanPos1.getValue()),
                getHumanUnitName(humanPos2.getValue()),
                getHumanUnitName(humanPos3.getValue())
        };
    }

    private HumanUnitName getHumanUnitName(String humanName) {
        if(humanName == null) return HumanUnitName.NONE;
        for(HumanUnitName name: HumanUnitName.values()) {
            if(humanName.equals(name.getName())) {
                return name;
            }
        }
        return HumanUnitName.NONE;
    }

    private WeaponName getTowerWeapon(){
        String weaponNameString = towerWeaponComboBox.getValue();
        if(weaponNameString.equals(LASER.getName())){
            return LASER;
        }else if(weaponNameString.equals(MINIGUN.getName())){
            return MINIGUN;
        }else{
            return HANDGUN;
        }
    }
}
