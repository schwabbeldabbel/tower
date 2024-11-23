package com.example.towerdef.model.data.weapon;

import lombok.Getter;

@Getter
public class Weapon {

    private String name;
    private int attackSpeed;
    private int damage;

    public Weapon(String name, int attackSpeed, int damage){
        this.name = name;
        this.attackSpeed = attackSpeed;
        this.damage = damage;
    }

}
