package com.example.towerdef.model.data.tower;

import com.example.towerdef.model.data.Hittable;
import com.example.towerdef.model.data.weapon.Weapon;
import com.example.towerdef.model.data.weapon.fxmlelement.Bullet;
import com.example.towerdef.model.gamelogic.review.GameStatistics;
import com.example.towerdef.model.gamelogic.review.HumanGameStatics;
import com.example.towerdef.model.gamelogic.review.TowerStatsName;
import lombok.Getter;
import lombok.Setter;

public class Tower implements Hittable {

    @Getter
    private String name;
    @Getter@Setter
    private int health;
    @Getter
    private int maxHealth;
    @Getter
    private Weapon weapon;

    @Getter
    private boolean alive;

    private float armor;
    private int healing;


    @Getter
    private boolean isMalfunctionOnCooldown;


    public Tower(String name, int health, Weapon weapon, float armor) {
        this.name = name;
        this.health = health;
        this.maxHealth = health;
        this.weapon = weapon;
        this.alive = true;
        this.armor = armor;
        this.healing = (int) (health * 1.25);
        this.isMalfunctionOnCooldown = false;
    }

    public String getStyleClass(){
        return "tower";
    }


    public void resetMalfunction(){
        this.isMalfunctionOnCooldown = false;
    }

    public Bullet shoot(){
        if(this.alive){
            GameStatistics.towerDamageFired += weapon.getDamage();
            return weapon.shoot();
        }else{
            return null;
        }
    }

    public int malfunction(){
        int damage = (int) ((health * 0.15) * armor);
        health -= damage;
        GameStatistics.malfunctionDamage += damage;
        isMalfunctionOnCooldown = true;
        return damage;
    }

    public void overdrive(int powerBullets){
        this.weapon.overdrive(powerBullets);
    }

    @Override
    public int hit(int damage) {
        int damageBlocked = (int) ((damage * armor));
        GameStatistics.towerDamageBlocked += damageBlocked;

        int damageTaken = damage - damageBlocked;
        GameStatistics.towerDamageTaken += damageTaken;

        this.health -= damageTaken;

        System.out.println("[ " + this.name + " ] hit: " + damageTaken);
        System.out.println("[ " + this.name + "] health: " + health);

        return damageTaken;
    }

    @Override
    public int heal() {
//        lifeHealed += healing;
//        health += healing;
        return 0;
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
