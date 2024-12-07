package com.example.towerdef.model.data.tower;

import com.example.towerdef.model.data.weapon.Weapon;
import com.example.towerdef.model.data.weapon.fxmlelement.Bullet;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class Tower {

    private String name;
    private int health;
    @Getter
    private Weapon weapon;

    public Tower(String name, int health, Weapon weapon){
        this.name = name;
        this.health = health;
        this.weapon = weapon;
    }

    public Bullet shoot(){
        return weapon.shoot();
    }
}
