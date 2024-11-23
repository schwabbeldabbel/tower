package com.example.towerdef.model.data.human;

import com.example.towerdef.model.data.weapon.Weapon;

public class HumanUnit {

    private String name;
    private int health;
    private Weapon weapon;
    private int healing;
    private float armor;
    private int position;

    public HumanUnit(HumanUnitBuilder builder) {
        this.name = builder.name;
        this.health = builder.health;
        this.weapon = builder.weapon;
        this.healing = builder.healing;
        this.armor = builder.armor;
        this.position = builder.position;
    }


    public static class HumanUnitBuilder {

        private String name;
        private int health;
        private Weapon weapon;
        private int healing;
        private float armor;
        private int position;

        public HumanUnitBuilder setName(String name) {
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
