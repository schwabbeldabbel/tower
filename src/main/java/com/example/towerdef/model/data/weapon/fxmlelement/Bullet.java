package com.example.towerdef.model.data.weapon.fxmlelement;

import javafx.scene.shape.Rectangle;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class Bullet extends Rectangle{

    private volatile boolean running;
    private BulletType bulletType;

    public Bullet(BulletType type) {
        super(type.getBulletWidth(), type.getBulletHeight(), type.getColor());
        this.bulletType = type;
    }
}
