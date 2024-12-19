package com.example.towerdef.model.gamelogic.runtime;

import com.example.towerdef.model.data.weapon.fxmlelement.Bullet;
import com.example.towerdef.model.gamelogic.time.Speed;
import com.example.towerdef.model.services.IdService;
import javafx.animation.KeyFrame;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;
import lombok.Setter;

public class TravelAnimations {

    @Setter
    private Speed speed = Speed.NORMAL;

    private GridPane root;

    public TravelAnimations(GridPane root){
        this.root = root;
    }


    public void initializeStartPosition(Bullet bullet, int startPosition, Path path) {
        switch (startPosition) {
            //col, row
            case 0:
                root.add(bullet, 3, 1);
                path.getElements().add(new MoveTo(100f, 80f));
                break;
            case 1:
                root.add(bullet, 1, 3);
                path.getElements().add(new MoveTo(100f, 40f));
                break;
            case 2:
                root.add(bullet, 4, 4);
                path.getElements().add(new MoveTo(100f, 80f));
                break;
            default:
                root.add(bullet, 7, 1);
                path.getElements().add(new MoveTo(50f, 50f));
                break;
        }
    }

    public void initializeTravelPath(Bullet bullet, Point2D target, Path path, PathTransition pathTransition) {
        if(target == null) return;
        path.getElements().add(new LineTo(target.getX(), target.getY()));
        pathTransition.setDuration(Duration.millis(bullet.getBulletType().getTravelTime() * speed.getMilliseconds()));
    }

    public void startDamageCountAnimation(Label damageLabel, Node target){
        Path path = new Path();
        PathTransition pathTransition = new PathTransition();
        int position = IdService.getPositionOfId(target.getId());
        initializeStartPositionUp(damageLabel, path, position);
        initializeTravelPathUp(path, pathTransition);

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(200 * speed.getMilliseconds()), event -> {
            root.getChildren().remove(damageLabel);
        }));
        timeline.setCycleCount(1);

        pathTransition.setNode(damageLabel);
        pathTransition.setPath(path);

        timeline.play();
        pathTransition.play();
    }

    private void initializeStartPositionUp(Node node, Path path, int position) {
        switch (position) {
            case 1 -> root.add(node, 3, 1);
            case 2 -> root.add(node, 1, 3);
            case 3 -> root.add(node, 4, 4);
            default -> root.add(node, 7, 1);
        }
        int randomDif = RandomSelector.getRandomDifToGiven(0, 50);
        path.getElements().add(new MoveTo(randomDif,randomDif));
    }

    private void initializeTravelPathUp(Path path, PathTransition pathTransition) {
        path.getElements().add(new LineTo(0, -60));
        pathTransition.setDuration(Duration.millis(200 * speed.getMilliseconds()));
    }


}
