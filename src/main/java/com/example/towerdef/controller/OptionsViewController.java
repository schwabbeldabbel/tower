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
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

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
    @FXML
    protected HBox humanBoxEngineer, humanBoxTank, humanBoxSniper;

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
        initToolTips();
    }

    private void initHumanNames() {
        humanLabel1.setText(HumanUnitName.ENGINEER.getFrontendName());
        humanLabel2.setText(HumanUnitName.TANK.getFrontendName());
        humanLabel3.setText(HumanUnitName.SNIPER.getFrontendName());
    }

    private void initComboBox() {
        ObservableList<String> humanClasses = FXCollections.observableArrayList(
                HumanUnitName.ENGINEER.getFrontendName(),
                HumanUnitName.TANK.getFrontendName(),
                HumanUnitName.SNIPER.getFrontendName(),
                HumanUnitName.NONE.getFrontendName()
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
            if (human.getPosition() == 0) {
                humanPos1.setValue(human.getName().getFrontendName());
                setHumansClass(humanPos1);
            } else if (human.getPosition() == 1) {
                humanPos2.setValue(humanUnits.get(1).getName().getFrontendName());
                setHumansClass(humanPos2);
            } else if (human.getPosition() == 2) {
                humanPos3.setValue(humanUnits.get(2).getName().getFrontendName());
                setHumansClass(humanPos3);
            }
        });
        if (humanPos1.getValue() == null) {
            humanPos1.setValue(HumanUnitName.NONE.getFrontendName());
            setHumansClass(humanPos1);
        }
        if (humanPos2.getValue() == null) {
            humanPos2.setValue(HumanUnitName.NONE.getFrontendName());
            setHumansClass(humanPos2);
        }
        if (humanPos3.getValue() == null) {
            humanPos3.setValue(HumanUnitName.NONE.getFrontendName());
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

    private void initToolTips() {
        Tooltip healthTextHumanTip = new Tooltip();
        healthTextHumanTip.getStyleClass().add("tooltip");
        healthTextHumanTip.setText("Das Basisleben der Menschen.\n" +
                "Die Menschen beziehen ihr Leben auf Grundlage von diesem Wert.\n" +
                "Weitere Informationen zu den Einheiten findest du unten,\n wenn du mit der Maus auf einem der Namen stehen bleibst.");
        Tooltip.install(humanHealthText, healthTextHumanTip);
        Tooltip.install(humanHealthSlider, healthTextHumanTip);

        Tooltip healthTextTowerTip = new Tooltip();
        healthTextTowerTip.setText("Das Leben von dem Turm.");
        healthTextTowerTip.getStyleClass().add("tooltip");
        Tooltip.install(towerHealthText, healthTextTowerTip);
        Tooltip.install(towerHealthSlider, healthTextTowerTip);

        Tooltip weaponTowerTip = new Tooltip();
        weaponTowerTip.getStyleClass().add("tooltip");
        weaponTowerTip.setText("Die Waffe mit welcher der Turm schießt. \n" +
                "Jede Waffe hat unterschiedliche Eigenschaften. \n" +
                "\n" +
                "Minigun: \n" +
                "- sehr hohes Angriffstempo\n" +
                "- geringer Schaden\n" +
                "\n" +
                "Laser:\n" +
                "- sehr geringes Angriffstempo\n" +
                "- sehr hoher Schaden\n" +
                "\n" +
                "Handpistole:\n" +
                "- mittleres Angriffstempo\n" +
                "- hoher Schaden\n");
        weaponTowerTip.setHideDelay(Duration.seconds(8));
        Tooltip.install(towerWeaponComboBox, weaponTowerTip);

        Tooltip engineerTooltip = new Tooltip();
        engineerTooltip.getStyleClass().add("tooltip");
        engineerTooltip.setText("Der Mechaniker\n" +
                "\n" +
                "Eine Einheit, die viel Schaden verursacht.\n\n" +
                "Waffe: Schraubenkanone (hoher Schaden, geringe Feuerrate)\n" +
                "Leben: Basisleben\n" +
                "Rüstung: 20% Schadensreduktion");
        Tooltip.install(humanBoxEngineer, engineerTooltip);

        Tooltip tankTooltip = new Tooltip();
        tankTooltip.getStyleClass().add("tooltip");
        tankTooltip.setText("Der Verteidiger\n" +
                "\n" +
                "Eine Einheit, die wenig Schaden verursacht aber viel einstecken kann.\n\n" +
                "Waffe: Leichtes Maschinengewehr (geringer Schaden, sehr hohe Feuerrate)\n" +
                "Leben: Basisleben x 2\n" +
                "Rüstung: 35% Schadensreduktion");
        Tooltip.install(humanBoxTank, tankTooltip);

        Tooltip sniperTooltip = new Tooltip();
        sniperTooltip.getStyleClass().add("tooltip");
        sniperTooltip.setText("Der Feuerteufel\n" +
                "\n" +
                "Eine Einheit, die sehr viel Schaden verursacht aber sehr wenig Schaden aushält.\n\n" +
                "Waffe: Scharfschützengewehr (sehr hoher Schaden, sehr geringe Feuerrate)\n" +
                "Leben: Basisleben x 0,5\n" +
                "Rüstung: 15% Schadensreduktion");
        Tooltip.install(humanBoxSniper, sniperTooltip);
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
    public void resetOptions() {
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
        if (humanName == null) return HumanUnitName.NONE;
        for (HumanUnitName name : HumanUnitName.values()) {
            if (humanName.equals(name.getFrontendName())) {
                return name;
            }
        }
        return HumanUnitName.NONE;
    }

    private WeaponName getTowerWeapon() {
        String weaponNameString = towerWeaponComboBox.getValue();
        if (weaponNameString.equals(LASER.getName())) {
            return LASER;
        } else if (weaponNameString.equals(MINIGUN.getName())) {
            return MINIGUN;
        } else {
            return HANDGUN;
        }
    }
}
