package com.example.towerdef.model.data.tower;

import com.example.towerdef.model.data.Hittable;
import com.example.towerdef.model.data.weapon.Weapon;
import com.example.towerdef.model.data.weapon.fxmlelement.Bullet;
import lombok.Getter;

public class Tower implements Hittable {

    private String name;
    @Getter
    private int health;
    @Getter
    private Weapon weapon;

    @Getter
    private boolean alive;

    private float armor;


    public Tower(String name, int health, Weapon weapon, float armor) {
        this.name = name;
        this.health = health;
        this.weapon = weapon;
        this.alive = true;
        this.armor = armor;
    }

    public Bullet shoot(){
        if(this.alive){
            return weapon.shoot();
        }else{
            return null;
        }
    }

    @Override
    public boolean hit(int damage) {
        this.health -= (int) (damage - (damage * armor));
        System.out.println("[ Tower ] health: " + health);
        return this.health > 0;
    }

    @Override
    public void heal() {
        this.health += 0;
    }

    @Override
    public float getArmor() {
        return 0;
    }

    @Override
    public void die(){
        this.alive = false;
    }
}
