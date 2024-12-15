package com.example.towerdef.model.data.tower;

import com.example.towerdef.model.data.Hittable;
import com.example.towerdef.model.data.weapon.Weapon;
import com.example.towerdef.model.data.weapon.fxmlelement.Bullet;
import com.example.towerdef.model.gamelogic.review.TowerStatsName;
import lombok.Getter;
import lombok.Setter;

public class Tower implements Hittable {

    @Getter
    private String name;
    @Getter@Setter
    private int health;
    @Getter
    private Weapon weapon;

    @Getter
    private boolean alive;

    private float armor;
    private int healing;

    @Getter
    private boolean isMalfunctionOnCooldown;

    @Getter
    private int damageFired = 0;
    @Getter
    private int damageTaken = 0;
    @Getter
    private int damageBlocked = 0;
    @Getter
    private int lifeHealed = 0;
    @Getter
    private int malfunctionDamage = 0;

    public Tower(String name, int health, Weapon weapon, float armor) {
        this.name = name;
        this.health = health;
        this.weapon = weapon;
        this.alive = true;
        this.armor = armor;
        this.healing = (int) (health * 1.25);
        this.isMalfunctionOnCooldown = false;
    }

    public int getData(TowerStatsName statsName){
        switch (statsName){
            case DAMAGE_DEALT -> {
                return damageFired;
            }
            case DAMAGE_TAKEN -> {
                return damageTaken;
            }
            case DAMAGE_BLOCKED -> {
                return damageBlocked;
            }
            case OVERDRIVE_DAMAGE -> {
                return weapon.getPowerBulletDamage();
            }
            case MALFUNCTION_DAMAGE -> {
                return malfunctionDamage;
            }
        }
        return -1;
    }

    public void resetMalfunction(){
        this.isMalfunctionOnCooldown = false;
    }

    public Bullet shoot(){
        if(this.alive){
            damageFired += weapon.getDamage();
            return weapon.shoot();
        }else{
            return null;
        }
    }

    public int malfunction(){
        int damage = (int) ((health * 0.15) * armor);
        health -= damage;
        malfunctionDamage += damage;
        isMalfunctionOnCooldown = true;
        return damage;
    }

    public void overdrive(int powerBullets){
        this.weapon.overdrive(powerBullets);
    }

    @Override
    public int hit(int damage) {
        int damageBlocked = (int) ((damage * armor));
        this.damageBlocked += damageBlocked;

        int damageTaken = damage - damageBlocked;
        this.damageTaken += damageTaken;

        this.health -= damageTaken;

        System.out.println("[ " + this.name + " ] hit: " + damageTaken);
        System.out.println("[ " + this.name + "] health: " + health);

        return damageTaken;
    }

    @Override
    public void heal() {
        lifeHealed += healing;
        health += healing;
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
