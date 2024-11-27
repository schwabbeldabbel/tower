package com.example.towerdef.model.data.weapon.fxmlelement;

import javafx.scene.shape.Rectangle;

public class Bullet extends Rectangle {

    private BulletType type;

    private boolean alive;

    public Bullet(int x, int y, BulletType type, int speed){
        super(type.getBulletWidth(), type.getBulletWidth(), type.getColor());
        this.type = type;
        setTranslateX(x);
        setTranslateY(y);
        this.alive = true;
        travel(speed);
    }

    private void travel(int speed){
        setTranslateX(getTranslateX() + speed);
    }

    public void hit(){
        this.alive = false;
    }

}
