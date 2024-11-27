package com.example.towerdef.controller;

import com.example.towerdef.model.data.human.HumanUnit;
import com.example.towerdef.model.data.weapon.Weapon;
import com.example.towerdef.model.data.weapon.fxmlelement.Bullet;
import com.example.towerdef.model.gamelogic.setup.GameSettings;
import com.example.towerdef.model.gamelogic.time.TimerThread;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

import java.util.List;

import static com.example.towerdef.model.gamelogic.time.Speed.*;


public class GameViewController {

    @FXML
    StackPane root;
    @FXML
    private Button humanPos1, humanPos2, humanPos3;
    @FXML
    private Label timer;

    private GameSettings gameSettings;
    private TimerThread timerThread;

    public void initialize() {
        this.gameSettings = GameSettings.getInstance();
        timerThread = new TimerThread(timer, NORMAL, gameSettings.getAllWeapons());
        initializeWeapons(gameSettings.getAllWeapons());
        Platform.runLater(() -> {
            setHumans(gameSettings.getHumanUnits());
            startTimer();
        });
    }

    public void addBullet(Bullet bullet) {
        root.getChildren().add(bullet);
    }


    private void startTimer() {
        timerThread.start();
    }

    private void setHumans(List<HumanUnit> humanUnits) {
        for (HumanUnit humanUnit : humanUnits) {
            switch (humanUnit.getPosition()) {
                case 0:
                    humanPos1.getStyleClass().add(humanUnit.getName().getCss());
                    Bounds boundsInScene1 = humanPos1.localToScene(humanPos1.getBoundsInLocal());
                    humanUnit.getWeapon().setXPos((int) boundsInScene1.getMinX());
                    humanUnit.getWeapon().setYPos((int) boundsInScene1.getMaxY());
                    break;
                case 1:
                    humanPos2.getStyleClass().add(humanUnit.getName().getCss());
                    humanUnit.getWeapon().setXPos((int) humanPos2.getLayoutX());
                    humanUnit.getWeapon().setYPos((int) humanPos2.getLayoutY());
                    break;
                case 2:
                    humanPos3.getStyleClass().add(humanUnit.getName().getCss());
                    humanUnit.getWeapon().setXPos((int) humanPos2.getLayoutX());
                    humanUnit.getWeapon().setYPos((int) humanPos2.getLayoutY());
                    break;
            }
        }
    }

    private void initializeWeapons(List<Weapon> weapons) {
        for (Weapon weapon : weapons) {
            weapon.addObserver(this);
        }
    }

    @FXML
    protected void setSpeed(Event event) {
        Button trigger = (Button) event.getSource();
        switch (trigger.getId()) {
            case "SLOW":
                timerThread.setSpeed(SLOW.getMiliseconds());
                break;
            case "NORMAL":
                timerThread.setSpeed(NORMAL.getMiliseconds());
                break;
            case "FAST":
                timerThread.setSpeed(FAST.getMiliseconds());
                break;
        }
    }


}
