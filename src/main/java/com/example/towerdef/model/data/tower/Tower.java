package com.example.towerdef.model.data.tower;

import com.example.towerdef.model.data.weapon.Weapon;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Tower {

    private String name;
    private float health;
    private Weapon weapon;

}
