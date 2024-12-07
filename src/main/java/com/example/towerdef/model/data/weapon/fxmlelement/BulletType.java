package com.example.towerdef.model.data.weapon.fxmlelement;

import javafx.scene.paint.Color;
import lombok.Getter;

@Getter
public enum BulletType {

    NORMAL(Color.GREY, 10, 10, 1000),
    BIG(Color.BLACK, 30, 30, 1500),
    DRILL(Color.LIGHTGRAY, 40, 40, 2500),
    LASER(Color.GREENYELLOW, 20, 500, 500),
    MINI(Color.RED, 5, 5, 900);

    private final Color color;
    private final int bulletHeight;
    private final int bulletWidth;
    private final int travelTime;

    BulletType(Color color, int height, int width, int travelTime){
        this.color = color;
        this.bulletHeight = height;
        this.bulletWidth = width;
        this.travelTime = travelTime;
    }
}
