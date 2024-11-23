package com.example.towerdef.model.data.tower;

import com.example.towerdef.model.data.weapon.Weapon;
import lombok.AllArgsConstructor;

public class Tower {

    private String name;
    private int health;
    private Weapon weapon;

    public Tower(String name, int health, Weapon weapon){
        this.name = name;
        this.health = health;
        this.weapon = weapon;
    }
}
