package com.example.towerdef.model.data.tower;

import com.example.towerdef.model.data.Hittable;
import com.example.towerdef.model.data.weapon.Weapon;
import com.example.towerdef.model.data.weapon.fxmlelement.Bullet;
import lombok.Getter;
import lombok.Setter;

public class Tower implements Hittable {

    private String name;
    @Getter@Setter
    private int health;
    @Getter
    private Weapon weapon;

    @Getter
    private boolean alive;

    private float armor;

    @Getter
    private boolean isMalfunctionOnCooldown;


    public Tower(String name, int health, Weapon weapon, float armor) {
        this.name = name;
        this.health = health;
        this.weapon = weapon;
        this.alive = true;
        this.armor = armor;
        this.isMalfunctionOnCooldown = false;
    }

    public void resetMalfunction(){
        this.isMalfunctionOnCooldown = false;
    }

    public Bullet shoot(){
        if(this.alive){
            return weapon.shoot();
        }else{
            return null;
        }
    }

    public int malfunction(){
        int damage = (int) ((health * 0.15) * armor);
        health -= damage;
        isMalfunctionOnCooldown = true;
        return damage;
    }

    @Override
    public int hit(int damage) {
        int damageTaken = (int) (damage - (damage * armor));
        this.health -= damageTaken;
        System.out.println("[ Tower ] health: " + health);
        return damageTaken;
    }

    @Override
    public void heal() {
        this.health += 0;
    }


    @Override
    public boolean isAlive(){
        return this.health > 0;
    }
    @Override
    public void die(){
        this.alive = false;
    }
}
