package com.example.towerdef.controller;

import com.example.towerdef.controller.scenes.SceneController;
import com.example.towerdef.controller.scenes.SceneNames;
import com.example.towerdef.model.data.Hittable;
import com.example.towerdef.model.data.human.HumanUnit;
import com.example.towerdef.model.data.tower.Tower;
import com.example.towerdef.model.data.weapon.fxmlelement.Bullet;
import com.example.towerdef.model.data.weapon.fxmlelement.BulletType;
import com.example.towerdef.model.gamelogic.review.GameStatistics;
import com.example.towerdef.model.gamelogic.runtime.GameplayTimer;
import com.example.towerdef.model.gamelogic.runtime.TravelAnimations;
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
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.*;
import javafx.util.Duration;

import java.util.*;

import static com.example.towerdef.model.gamelogic.time.Speed.*;


public class GameViewController {

    @FXML
    GridPane root;

    @FXML
    private Button humanPos1, humanPos2, humanPos3, statsBtn, towerPos;
    @FXML
    private Label timer;

    @FXML
    private Label winningLabel;
    @FXML
    private ProgressBar humanPos3Health, humanPos2Health, humanPos1Health, towerHealth;

    private List<Node> collidingNodes;

    private TimerThread timerThread;

    private List<HumanUnit> humans;
    private Tower tower;

    private final Map<Node, Point2D> positionTarget;
    private final Map<Node, Hittable> positionHittable;
    private final Map<Hittable, Node> hittablePosition;
    private final Map<Bullet, Timeline> activeBulletsTimeline;
    private Map<Hittable, ProgressBar> hittableHealthBar;

    private TravelAnimations travelAnimations;

    private final Validator validator;
    private final GameplayTimer gameplayTimer;

    public GameViewController(){
        positionTarget = new HashMap<>();
        positionHittable = new HashMap<>();
        hittablePosition = new HashMap<>();
        activeBulletsTimeline = new HashMap<>();
        hittableHealthBar = new HashMap<>();
        validator = new Validator();
        gameplayTimer = new GameplayTimer();
        collidingNodes = new ArrayList<>();
    }

    public void initialize() {
        GameSettings gameSettings = GameSettings.getInstance();
        timerThread = new TimerThread(NORMAL, this);
        humans = gameSettings.getNewHumanUnits();
        tower = gameSettings.getNewTower();
        resetData();
        towerPos.getStyleClass().add(tower.getStyleClass());
        towerPos.getStyleClass().add(tower.getStyleClass() + "-game");
        placeHumans();
        positionTarget.put(towerPos, new Point2D(900, 0));
        positionHittable.put(towerPos, tower);
        hittablePosition.put(tower, towerPos);
        hittableHealthBar.put(tower, towerHealth);
        towerHealth.setProgress(1);
        collidingNodes.add(humanPos1);
        collidingNodes.add(humanPos2);
        collidingNodes.add(humanPos3);
        collidingNodes.add(towerPos);
        travelAnimations = new TravelAnimations(root);
        Platform.runLater(this::startTimer);
    }

    public void notify(int milliSeconds) {
        setTimer(milliSeconds);
        checkShooting(milliSeconds);
        if(milliSeconds % 20 == 0){
            if(tower.getWeapon().getPowerBullets() < 0) {
                tower.resetMalfunction();
                int overDriveBullets = RandomSelector.isTowerOverdrive(tower.getHealth(), GameSettings.getInstance().getBaseHealthTower());
                if (overDriveBullets > -1) {
                    tower.overdrive(overDriveBullets);
                }
            }
            List<HumanUnit> healingHumans = humans.stream()
                    .filter(human -> RandomSelector.getIsHealing(human.getHealth(), human.getMaxHealth()))
                    .filter(human -> human.getHealing() != 0)
                    .toList();
            for(HumanUnit human : healingHumans) {
                hit(hittablePosition.get(human), human.heal(), BulletType.HEAL.getStyleClass());
            }
        }
    }

    private void addBullet(Bullet bullet, int startPosition, Node target, int damage) {
        Path path = new Path();
        PathTransition pathTransition = new PathTransition();

        travelAnimations.initializeStartPosition(bullet, startPosition, path);
        Point2D targetPoint = positionTarget.get(target);
        if(startPosition == 0){
            targetPoint = new Point2D(targetPoint.getX(), targetPoint.getY() + 200);
        }
        travelAnimations.initializeTravelPath(bullet, targetPoint, path, pathTransition);

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
            if (validator.isColliding(bullet, target.getParent())) {
                root.getChildren().remove(bullet);
                hit(target, damage, bullet.getBulletType().getStyleClass());
                activeBulletsTimeline.get(bullet).stop();
                activeBulletsTimeline.remove(bullet);
            }
        }));
    }

    private void towerMalfunction(boolean isTowerMalfunction) {
        if(isTowerMalfunction){
            hit(towerPos, tower.malfunction(), BulletType.EXTRA.getStyleClass());
        }
    }

    public void hit(Node target, int damage, String styleClass) {
        if(damage <= 0) return;
        if(target.getId().equals(towerPos.getId()) && !tower.isMalfunctionOnCooldown()){
            towerMalfunction(RandomSelector.isTowerMalfunction(tower.getHealth()));
        }
        Hittable hittable = positionHittable.get(target);
        int damageTaken = hittable.hit(damage);
        hittableHealthBar.get(hittable).setProgress((double) hittable.getHealth() / hittable.getMaxHealth());

        Label damageLabel = new Label(String.valueOf(damageTaken));
        damageLabel.getStyleClass().add(styleClass);
        travelAnimations.startDamageCountAnimation(damageLabel, target);

        boolean alive = hittable.isAlive();
        if(!alive){
            target.getStyleClass().remove(hittable.getStyleClass());
            target.getStyleClass().add(hittable.getStyleClass() + "-dead");
            hittable.die();
        }
        String winning = validator.isWinning(humans, tower);
        if(winning != null){
            end(winning);
        }
    }

    private void resetData(){
        GameStatistics.resetData(humans);
    }

    private void end(String text){
        winningLabel.setText(text);
        timerThread.stop();
        System.out.println("------------------------------------END-----------------------------------------");
        statsBtn.setDisable(false);
    }

    private void checkShooting(int milliSeconds) {
        for (HumanUnit humanUnit : humans) {
            if(gameplayTimer.checkHumanShooting(milliSeconds, humanUnit)){
                Bullet bullet = humanUnit.shoot();
                if(bullet != null) {
                    addBullet(bullet, humanUnit.getPosition(), towerPos, humanUnit.getWeapon().getDamage());
                }
            }
        }
        if (gameplayTimer.checkTowerShooting(milliSeconds, tower)) {
            Bullet bullet = tower.shoot();
            if(bullet != null){
                addBullet(bullet, -1, RandomSelector.updateSelectedHumanTarget(humans, hittablePosition), tower.getWeapon().getDamage());
            }
        }
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
                    positionTarget.put(humanPos1, new Point2D(-900, 50));
                    hittableHealthBar.put(humanUnit, humanPos1Health);
                    humanPos1Health.setProgress((double) humanUnit.getHealth() / humanUnit.getMaxHealth());
                    positionHittable.put(humanPos1, humanUnit);
                    hittablePosition.put(humanUnit, humanPos1);
                    break;
                case 1:
                    humanPos2.getStyleClass().add(humanUnit.getName().getCss());
                    positionTarget.put(humanPos2, new Point2D(-1000, 400));
                    hittableHealthBar.put(humanUnit, humanPos2Health);
                    humanPos2Health.setProgress((double) humanUnit.getHealth() / humanUnit.getMaxHealth());
                    positionHittable.put(humanPos2, humanUnit);
                    hittablePosition.put(humanUnit, humanPos2);
                    break;
                case 2:
                    humanPos3.getStyleClass().add(humanUnit.getName().getCss());
                    positionTarget.put(humanPos3, new Point2D(-900, 900));
                    hittableHealthBar.put(humanUnit, humanPos3Health);
                    humanPos3Health.setProgress((double) humanUnit.getHealth() / humanUnit.getMaxHealth());
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
                travelAnimations.setSpeed(SLOW);
                break;
            case "NORMAL":
                timerThread.setSpeed(NORMAL.getMiliseconds());
                travelAnimations.setSpeed(NORMAL);
                break;
            case "FAST":
                timerThread.setSpeed(FAST.getMiliseconds());
                travelAnimations.setSpeed(FAST);
                break;
        }
    }

    @FXML
    public void backToStart(){
        end("Abbruch");
        SceneController sceneController = SceneController.getInstance();
        sceneController.activate(SceneNames.MAIN);
    }

    @FXML
    public void showStats(){
        SceneController.getInstance().activate(SceneNames.STATS);
    }
}