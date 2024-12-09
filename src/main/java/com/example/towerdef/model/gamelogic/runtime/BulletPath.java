package com.example.towerdef.model.gamelogic.runtime;

import com.example.towerdef.model.data.weapon.fxmlelement.Bullet;
import com.example.towerdef.model.data.weapon.fxmlelement.BulletType;
import com.example.towerdef.model.gamelogic.time.Speed;
import javafx.animation.PathTransition;
import javafx.geometry.Point2D;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;
import lombok.Setter;

public class BulletPath {

    @Setter
    private Speed speed = Speed.NORMAL;

    public void initializeTravelPath(Bullet bullet, Point2D target, Path path, PathTransition pathTransition) {
        path.getElements().add(new LineTo(target.getX(), target.getY()));

        switch (bullet.getBulletType()) {
            case NORMAL -> {
                pathTransition.setDuration(Duration.millis(BulletType.NORMAL.getTravelTime() * speed.getMiliseconds()));
            }
            case BIG -> {
                pathTransition.setDuration(Duration.millis(BulletType.BIG.getTravelTime() * speed.getMiliseconds()));
            }
            case DRILL -> {
                pathTransition.setDuration(Duration.millis(BulletType.DRILL.getTravelTime() * speed.getMiliseconds()));
            }
            case LASER -> {
                pathTransition.setDuration(Duration.millis(BulletType.LASER.getTravelTime() * speed.getMiliseconds()));
            }
            case MINI -> {
                pathTransition.setDuration(Duration.millis(BulletType.MINI.getTravelTime() * speed.getMiliseconds()));
            }
        }
    }

    public void initializeStartPosition(Bullet bullet, int startPosition, Path path, GridPane root) {
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

}
