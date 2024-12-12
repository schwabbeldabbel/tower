package com.example.towerdef.model.data.weapon.fxmlelement;

import javafx.scene.paint.Color;
import lombok.Getter;

@Getter
public enum BulletType {

    NORMAL(Color.GREY, 10, 10, 100, "damageNumberNormal"),
    BIG(Color.BLACK, 10, 30, 150, "damageNumberBig"),
    DRILL(Color.LIGHTGRAY, 40, 40, 250, "damageNumberDrill"),
    LASER(Color.GREENYELLOW, 20, 500, 50, "damageNumberLaser"),
    MINI(Color.RED, 5, 5, 90, "damageNumberMini"),
    EXTRA(Color.BLACK, 0, 0, 0, "damageNumberExtra");

    private final Color color;
    private final int bulletHeight;
    private final int bulletWidth;
    private final int travelTime;
    private final String styleClass;

    BulletType(Color color, int height, int width, int travelTime, String styleClass) {
        this.color = color;
        this.bulletHeight = height;
        this.bulletWidth = width;
        this.travelTime = travelTime;
        this.styleClass = styleClass;
    }
}
