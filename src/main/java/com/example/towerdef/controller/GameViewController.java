package com.example.towerdef.controller;

import com.example.towerdef.controller.scenes.SceneController;
import com.example.towerdef.controller.scenes.SceneNames;
import com.example.towerdef.model.data.Hittable;
import com.example.towerdef.model.data.human.HumanUnit;
import com.example.towerdef.model.data.tower.Tower;
import com.example.towerdef.model.data.weapon.Weapon;
import com.example.towerdef.model.data.weapon.fxmlelement.Bullet;
import com.example.towerdef.model.gamelogic.runtime.BulletPath;
import com.example.towerdef.model.gamelogic.runtime.RandomSelector;
import com.example.towerdef.model.gamelogic.runtime.Validator;
import com.example.towerdef.model.gamelogic.setup.GameSettings;
import com.example.towerdef.model.gamelogic.time.TimerThread;
import javafx.animation.KeyFrame;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.*;
import javafx.util.Duration;

import java.util.*;

import static com.example.towerdef.model.gamelogic.time.Speed.*;


public class GameViewController {

    @FXML
    GridPane root;
    @FXML
    Rectangle towerPos;
    @FXML
    private Button humanPos1, humanPos2, humanPos3;
    @FXML
    private Label timer;
    @FXML
    private Label winningLabel;

    private List<Node> collidingNodes;

    private TimerThread timerThread;

    private List<HumanUnit> humans;
    private Tower tower;
    private int selectedHumanPos;

    private final Map<Node, Point2D> positionTarget;
    private final Map<Node, Hittable> positionHittable;
    private final Map<Hittable, Node> hittablePosition;
    private final Map<Bullet, Timeline> activeBulletsTimeline;


    private final Validator validator;
    private final BulletPath bulletPath;

    public GameViewController(){
        positionTarget = new HashMap<>();
        positionHittable = new HashMap<>();
        hittablePosition = new HashMap<>();
        activeBulletsTimeline = new HashMap<>();
        validator = new Validator();
        bulletPath = new BulletPath();
        collidingNodes = new ArrayList<>();
        selectedHumanPos = 1;
    }

    public void initialize() {
        GameSettings gameSettings = GameSettings.getInstance();
        timerThread = new TimerThread(NORMAL, this);
        humans = gameSettings.getHumanUnits();
        tower = gameSettings.getTower();
        placeHumans();
        positionTarget.put(towerPos, new Point2D(900, 0));
        positionHittable.put(towerPos, tower);
        hittablePosition.put(tower, towerPos);
        collidingNodes.add(humanPos1);
        collidingNodes.add(humanPos2);
        collidingNodes.add(humanPos3);
        collidingNodes.add(towerPos);
        Platform.runLater(this::startTimer);
    }

    public void notify(int milliSeconds) {
        setTimer(milliSeconds);
        checkShooting(milliSeconds);
        if (milliSeconds % 1000 == 0) {
            selectedHumanPos = RandomSelector.updateSelectedHumanTarget(humans.size());
        }
    }

    private void addBullet(Bullet bullet, int startPosition, Node target, int damage) {
        Path path = new Path();
        PathTransition pathTransition = new PathTransition();

        bulletPath.initializeStartPosition(bullet, startPosition, path, root);

        bulletPath.initializeTravelPath(bullet, positionTarget.get(target), path, pathTransition);

        pathTransition.setNode(bullet);
        pathTransition.setPath(path);
        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);

        Timeline timeline = setUpCollisionDetection(bullet, target, damage);
        timeline.setCycleCount(300);
        timeline.setOnFinished(event -> {
            root.getChildren().remove(bullet);
        });
        timeline.play();

        activeBulletsTimeline.put(bullet, timeline);

        pathTransition.play();
    }

    private Timeline setUpCollisionDetection(Bullet bullet, Node target, int damage) {
       return new Timeline(new KeyFrame(Duration.millis(10), event -> {
            if (validator.isColliding(bullet, target)) {
                root.getChildren().remove(bullet);
                hit(target, damage);
                activeBulletsTimeline.get(bullet).stop();
                activeBulletsTimeline.remove(bullet);
            }
        }));
    }

    private void hit(Node target, int damage){
        Hittable hittable = positionHittable.get(target);
        if(!hittable.hit(damage)){
            root.getChildren().remove(target);
            hittable.die();
        }
        String winning = validator.isWinning(humans, tower);
        if(winning != null){
            end(winning);
        }
    }

    private void end(String text){
        winningLabel.setText(text);
        timerThread.stop();
        GameSettings.removeInstance();
    }

    private void checkShooting(int milliSeconds) {
        for (HumanUnit humanUnit : humans) {
            Weapon weapon = humanUnit.getWeapon();
            if (milliSeconds % weapon.getAttackSpeed() == 0) {
                Bullet bullet = humanUnit.shoot();
                if(bullet != null){
                    addBullet(bullet, humanUnit.getPosition(), towerPos, weapon.getDamage());
                }
            }
        }
        if (milliSeconds % tower.getWeapon().getAttackSpeed() == 0) {
            Bullet bullet = tower.shoot();
            if(bullet != null) {
                addBullet(bullet, -1, getSelectedTarget(), tower.getWeapon().getDamage());
            }
        }
    }

    private Node getSelectedTarget() {
        return hittablePosition.get(humans.get(selectedHumanPos-1));
    }

    private void setTimer(int milliSeconds) {
        timer.setText("Sekunden: " + milliSeconds / 100);
    }

    private void startTimer() {
        timerThread.start();
    }

    private void placeHumans() {
        for (HumanUnit humanUnit : humans) {
            switch (humanUnit.getPosition()) {
                case 0:
                    humanPos1.getStyleClass().add(humanUnit.getName().getCss());
                    positionTarget.put(humanPos1, new Point2D(-900, -50));
                    positionHittable.put(humanPos1, humanUnit);
                    hittablePosition.put(humanUnit, humanPos1);
                    break;
                case 1:
                    humanPos2.getStyleClass().add(humanUnit.getName().getCss());
                    positionTarget.put(humanPos2, new Point2D(-1000, 400));
                    positionHittable.put(humanPos2, humanUnit);
                    hittablePosition.put(humanUnit, humanPos2);
                    break;
                case 2:
                    humanPos3.getStyleClass().add(humanUnit.getName().getCss());
                    positionTarget.put(humanPos3, new Point2D(-900, 900));
                    positionHittable.put(humanPos3, humanUnit);
                    hittablePosition.put(humanUnit, humanPos3);
                    break;
            }
        }
    }

    @FXML
    protected void setSpeed(Event event) {
        Button trigger = (Button) event.getSource();
        switch (trigger.getId()) {
            case "SLOW":
                timerThread.setSpeed(SLOW.getMiliseconds());
                bulletPath.setSpeed(SLOW);
                break;
            case "NORMAL":
                timerThread.setSpeed(NORMAL.getMiliseconds());
                bulletPath.setSpeed(NORMAL);
                break;
            case "FAST":
                timerThread.setSpeed(FAST.getMiliseconds());
                bulletPath.setSpeed(FAST);
                break;
        }
    }
    @FXML
    public void backToStart(){
        end("Abbruch");
        SceneController sceneController = SceneController.getInstance();
        sceneController.activate(SceneNames.MAIN);
    }
}