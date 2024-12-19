package com.example.towerdef.model.data.weapon.fxmlelement;

import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class Bullet extends Region {

    private volatile boolean running;
    private BulletType bulletType;

    public Bullet(BulletType type) {
        super();
        this.setMinWidth(type.getBulletWidth());
        this.setMaxWidth(type.getBulletWidth());
        this.setMinHeight(type.getBulletHeight());
        this.setMaxHeight(type.getBulletHeight());
        this.getStyleClass().add(type.getBulletCss());
        this.getStyleClass().add("background-image");
        this.bulletType = type;
    }
}
