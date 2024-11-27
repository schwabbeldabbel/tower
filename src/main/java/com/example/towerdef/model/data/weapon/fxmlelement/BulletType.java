package com.example.towerdef.model.data.weapon.fxmlelement;

import javafx.scene.paint.Color;
import lombok.Getter;

@Getter
public enum BulletType {

    NORMAL(Color.GREY, 10, 10),
    BIG(Color.BLACK, 30, 30),
    DRILL(Color.LIGHTGRAY, 40, 40),
    LASER(Color.GREENYELLOW, 20, 20),
    MINI(Color.RED, 5, 5);

    private final Color color;
    private final int bulletHeight;
    private final int bulletWidth;

    private BulletType(Color color, int height, int width){
        this.color = color;
        this.bulletHeight = height;
        this.bulletWidth = width;
    }
}
