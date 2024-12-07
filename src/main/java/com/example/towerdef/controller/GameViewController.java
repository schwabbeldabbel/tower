package com.example.towerdef.controller;

import com.example.towerdef.model.data.Hittable;
import com.example.towerdef.model.data.human.HumanUnit;
import com.example.towerdef.model.data.tower.Tower;
import com.example.towerdef.model.data.weapon.Weapon;
import com.example.towerdef.model.data.weapon.fxmlelement.Bullet;
import com.example.towerdef.model.data.weapon.fxmlelement.BulletType;
import com.example.towerdef.model.gamelogic.setup.GameSettings;
import com.example.towerdef.model.gamelogic.time.TimerThread;
import javafx.animation.KeyFrame;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
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

    private Map<Node, Point2D> positionTarget;
    private Map<Node, Hittable> targetHittable;
    private Map<Bullet, Timeline> activeBulletsTimeline;

    public void initialize() {
        GameSettings gameSettings = GameSettings.getInstance();
        timerThread = new TimerThread(NORMAL, this);
        this.humans = gameSettings.getHumanUnits();
        this.tower = gameSettings.getTower();
        this.positionTarget = new HashMap<>();
        this.targetHittable = new HashMap<>();
        this.activeBulletsTimeline = new HashMap<>();
        placeHumans();
        this.positionTarget.put(towerPos, new Point2D(900, 0));
        this.targetHittable.put(towerPos, tower);
        this.selectedHumanPos = 0;
        this.collidingNodes = new ArrayList<>();
        this.collidingNodes.add(humanPos1);
        this.collidingNodes.add(humanPos2);
        this.collidingNodes.add(humanPos3);
        this.collidingNodes.add(towerPos);
        Platform.runLater(this::startTimer);
    }

    private void addBullet(Bullet bullet, int startPosition, Node target, int damage) {
        Path path = new Path();
        PathTransition pathTransition = new PathTransition();

        initializeStartPosition(bullet, startPosition, path);

        initializeTravelPath(bullet, this.positionTarget.get(target), path, pathTransition);

        pathTransition.setNode(bullet);
        pathTransition.setPath(path);
        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);

        Timeline timeline = setUpCollisionDetection(bullet, target, damage);
        timeline.setCycleCount(100);
        timeline.play();

        this.activeBulletsTimeline.put(bullet, timeline);

        pathTransition.play();
    }

    private Timeline setUpCollisionDetection(Bullet bullet, Node target, int damage) {
       return new Timeline(new KeyFrame(Duration.millis(10), event -> {
            if (isColliding(bullet, target)) {
                root.getChildren().remove(bullet);
                hit(target, damage);
                this.activeBulletsTimeline.get(bullet).stop();
                this.activeBulletsTimeline.remove(bullet);
            }
        }));
    }

    private void hit(Node target, int damage){
        Hittable hittable = this.targetHittable.get(target);
        if(!hittable.hit(damage)){
            this.root.getChildren().remove(target);
            hittable.die();
        }
        checkWinning();
    }

    private void checkWinning(){
        int humanDeaths = 0;
        for(HumanUnit humanUnit: humans){
            if(!humanUnit.isAlive()){
                humanDeaths++;
            }
        }
        if(humanDeaths == humans.size()){
           towerWin();
            return;
        }
        if(!tower.isAlive()){
            humansWin();
        }
    }

    private void humansWin(){
        this.winningLabel.setText("Die Menschen haben gewonnen!!!");
        this.timerThread.stop();
    }
    private void towerWin(){
        this.winningLabel.setText("Der Turm hat gewonnen!!!");
        this.timerThread.stop();
    }

    private static void initializeTravelPath(Bullet bullet, Point2D target, Path path, PathTransition pathTransition) {
        path.getElements().add(new LineTo(target.getX(), target.getY()));

        switch (bullet.getBulletType()) {
            case NORMAL -> {
                pathTransition.setDuration(Duration.millis(BulletType.NORMAL.getTravelTime()));
            }
            case BIG -> {
                pathTransition.setDuration(Duration.millis(BulletType.BIG.getTravelTime()));
            }
            case DRILL -> {
                pathTransition.setDuration(Duration.millis(BulletType.DRILL.getTravelTime()));
            }
            case LASER -> {
                pathTransition.setDuration(Duration.millis(BulletType.LASER.getTravelTime()));
            }
            case MINI -> {
                pathTransition.setDuration(Duration.millis(BulletType.MINI.getTravelTime()));
            }
        }
    }

    private void initializeStartPosition(Bullet bullet, int startPosition, Path path) {
        switch (startPosition) {
            //col, row
            case 0:
                root.add(bullet, 3, 1);
                path.getElements().add(new MoveTo(150f, 20f));
                break;
            case 1:
                root.add(bullet, 1, 3);
                path.getElements().add(new MoveTo(150f, 50f));
                break;
            case 2:
                root.add(bullet, 4, 4);
                path.getElements().add(new MoveTo(150f, 50f));
                break;
            default:
                root.add(bullet, 7, 1);
                path.getElements().add(new MoveTo(50f, 50f));
                break;
        }
    }

    private boolean isColliding(Node bullet, Node target) {
        Bounds bulletBounds = bullet.getBoundsInParent();
        Bounds targetBounds = target.getBoundsInParent();
        return  bulletBounds.intersects(targetBounds);
    }

    public void notify(int milliSeconds) {
        setTimer(milliSeconds);
        checkShooting(milliSeconds);
        if (milliSeconds % 1000 == 0) {
            updateSelectedTarget();
        }
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
        return switch (this.selectedHumanPos) {
            case 0 -> humanPos1;
            case 1 -> humanPos2;
            default -> humanPos3;
        };
    }

    private void updateSelectedTarget() {
        Random random = new Random();
        this.selectedHumanPos = random.nextInt(0, 3);
    }

    private void setTimer(int milliSeconds) {
        timer.setText("Sekunden: " + milliSeconds / 1000);
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
                    targetHittable.put(humanPos1, humanUnit);
                    break;
                case 1:
                    humanPos2.getStyleClass().add(humanUnit.getName().getCss());
                    positionTarget.put(humanPos2, new Point2D(-1000, 400));
                    targetHittable.put(humanPos2, humanUnit);
                    break;
                case 2:
                    humanPos3.getStyleClass().add(humanUnit.getName().getCss());
                    positionTarget.put(humanPos3, new Point2D(-900, 900));
                    targetHittable.put(humanPos3, humanUnit);
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
