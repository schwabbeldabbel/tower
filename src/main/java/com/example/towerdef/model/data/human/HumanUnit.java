package com.example.towerdef.model.data.human;

import com.example.towerdef.model.data.Hittable;
import com.example.towerdef.model.data.weapon.Weapon;
import com.example.towerdef.model.data.weapon.fxmlelement.Bullet;
import com.example.towerdef.model.gamelogic.review.HumanStatsName;
import lombok.Getter;
import lombok.Setter;

@Getter
public class HumanUnit implements Hittable {

    private HumanUnitName name;
    @Setter
    private int health;
    @Getter
    private final int maxHealth;
    private Weapon weapon;
    private int healing;
    private float armor;
    private int position;
    private boolean alive;

    @Getter
    private int damageFired = 0;
    @Getter
    private int damageTaken = 0;
    @Getter
    private int damageBlocked = 0;
    @Getter
    private int lifeHealed = 0;

    public HumanUnit(HumanUnitBuilder builder) {
        this.name = builder.name;
        this.health = builder.health;
        this.maxHealth = builder.health;
        this.weapon = builder.weapon;
        this.healing = (int) (this.health * 0.3);
        this.armor = builder.armor;
        this.position = builder.position;
        this.alive = builder.isAlive;
    }

    public Bullet shoot(){
        if(this.alive){
            damageFired += weapon.getDamage();
            return weapon.shoot();
        }else{
            return null;
        }
    }

    public String getStyleClass(){
        return name.getCss();
    }

    public int getData(HumanStatsName humanStatsName){
        switch (humanStatsName){
            case DAMAGE_DEALT -> {
                return damageFired;
            }
            case DAMAGE_TAKEN -> {
                return damageTaken;
            }
            case DAMAGE_BLOCKED -> {
                return damageBlocked;
            }
            case LIFE_HEALED -> {
                return lifeHealed;
            }
        }
        return -1;
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
        healing = 0;
    }

    @Override
    public boolean isAlive(){
        return this.health > 0;
    }

    public void die(){
        this.alive = false;
    }



    public static class HumanUnitBuilder {

        private HumanUnitName name;
        private int health;
        private Weapon weapon;
        private float armor;
        private int position;
        private boolean isAlive = true;

        public HumanUnitBuilder setName(HumanUnitName name) {
            this.name = name;
            return this;
        }

        public HumanUnitBuilder setHealth(int health) {
            this.health = health;
            return this;
        }

        public HumanUnitBuilder setWeapon(Weapon weapon) {
            this.weapon = weapon;
            return this;
        }

        public HumanUnitBuilder setArmor(float armor) {
            this.armor = armor;
            return this;
        }

        public HumanUnitBuilder setPosition(int position) {
            this.position = position;
            return this;
        }

        public HumanUnitBuilder setIsAlive(boolean isAlive){
            this.isAlive = isAlive;
            return this;
        }

        public HumanUnit build() {
            return new HumanUnit(this);
        }

    }
}
