package com.example.towerdef.model.data.weapon.fxmlelement;

import lombok.Getter;

@Getter
public enum BulletType {

    NORMAL("bullet-normal", 10, 30, 100, "damageNumberNormal"),
    FIRE_BALL("bullet-fire-ball", 20, 30, 150, "damageNumberBig"),
    SUPER("bullet-super", 20, 30, 200, "damageNumberBig"),
    DRILL("bullet-drill", 40, 80, 250, "damageNumberDrill"),
    LASER("bullet-laser", 10, 200, 50, "damageNumberLaser"),
    MINI("bullet-mini", 6, 12, 90, "damageNumberMini"),
    EXTRA("bullet-extra", 0, 0, 0, "damageNumberExtra"),
    HEAL("bullet-heal", 0, 0, 0, "damageNumberHeal");

    private final String bulletCss;
    private final int bulletHeight;
    private final int bulletWidth;
    private final int travelTime;
    private final String styleClass;

    BulletType(String bulletCss, int height, int width, int travelTime, String styleClass) {
        this.bulletCss = bulletCss;
        this.bulletHeight = height;
        this.bulletWidth = width;
        this.travelTime = travelTime;
        this.styleClass = styleClass;
    }
}
