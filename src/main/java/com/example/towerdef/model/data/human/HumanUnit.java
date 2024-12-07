package com.example.towerdef.model.data.human;

import com.example.towerdef.model.data.Hittable;
import com.example.towerdef.model.data.weapon.Weapon;
import com.example.towerdef.model.data.weapon.fxmlelement.Bullet;
import lombok.Getter;
import lombok.Setter;

@Getter
public class HumanUnit implements Hittable {

    private HumanUnitName name;
    @Setter
    private int health;
    private Weapon weapon;
    private int healing;
    private float armor;
    private int position;
    private boolean alive;

    public HumanUnit(HumanUnitBuilder builder) {
        this.name = builder.name;
        this.health = builder.health;
        this.weapon = builder.weapon;
        this.healing = builder.healing;
        this.armor = builder.armor;
        this.position = builder.position;
        this.alive = true;
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
        this.health -= damage;
        return this.health > 0;
    }

    @Override
    public void heal() {
        this.health += this.healing;
    }

    @Override
    public void die(){
        this.alive = false;
    }


    public static class HumanUnitBuilder {

        private HumanUnitName name;
        private int health;
        private Weapon weapon;
        private int healing;
        private float armor;
        private int position;

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

        public HumanUnitBuilder setHealing(int healing) {
            this.healing = healing;
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

        public HumanUnit build() {
            return new HumanUnit(this);
        }

    }
}
