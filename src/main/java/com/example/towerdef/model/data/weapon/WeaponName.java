package com.example.towerdef.model.data.weapon;

import lombok.Getter;

public enum WeaponName {

    //Human
    LMG("LMG"),
    SNIPER("Scharfschützengewehr"),
    DRILL_CANON("Bohrerkanone"),

    //Tower
    HANDGUN("Handpistole"),
    MINIGUN("Minigun"),
    LASER("Laser");

    @Getter
    private final String name;

    private WeaponName(String name){
        this.name = name;
    }

}
