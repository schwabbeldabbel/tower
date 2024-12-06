package com.example.towerdef.model.data.weapon.fxmlelement;

import javafx.scene.paint.Color;
import lombok.Getter;

@Getter
public enum BulletType {

    NORMAL(Color.GREY, 10, 10, 30),
    BIG(Color.BLACK, 30, 30, 50),
    DRILL(Color.LIGHTGRAY, 40, 40, 60),
    LASER(Color.GREENYELLOW, 20, 20, 1),
    MINI(Color.RED, 5, 5, 15);

    private final Color color;
    private final int bulletHeight;
    private final int bulletWidth;
    private final int travelTime;

    private BulletType(Color color, int height, int width, int travelTime){
        this.color = color;
        this.bulletHeight = height;
        this.bulletWidth = width;
        this.travelTime = travelTime;
    }
}
