package com.example.towerdef.model.data.weapon.fxmlelement;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Bullet extends Rectangle {

    private String type;

    public Bullet(int x, int y, int w, int h, String type, Color color, int speed){
        super(w, h, color);
        this.type = type;
        setTranslateX(x);
        setTranslateY(y);

        startTravel(speed);
    }

    private void startTravel(int speed){
        setTranslateX(getTranslateX() + speed);
    }

}
