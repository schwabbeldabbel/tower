package com.example.towerdef.model.data.human;

import com.example.towerdef.model.data.Hittable;
import com.example.towerdef.model.data.weapon.Weapon;
import com.example.towerdef.model.data.weapon.fxmlelement.Bullet;
import com.example.towerdef.model.gamelogic.review.GameStatistics;
import com.example.towerdef.model.gamelogic.review.HumanGameStatics;
import lombok.Getter;
import lombok.Setter;

@Getter
public class HumanUnit implements Hittable {

    @Getter
    private final int maxHealth;
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
        this.maxHealth = builder.health;
        this.weapon = builder.weapon;
        this.healing = (int) (this.health * 0.45);
        this.armor = builder.armor;
        this.position = builder.position;
        this.alive = builder.isAlive;
    }

    public Bullet shoot() {
        if (this.alive) {
            HumanGameStatics humanStats = GameStatistics.getHumanStats(position);
            assert humanStats != null;
            humanStats.setDamageFired(humanStats.getDamageFired() + weapon.getDamage());
            return weapon.shoot();
        } else {
            return null;
        }
    }

    public String getStyleClass() {
        return name.getCss();
    }


    @Override
    public int hit(int damage) {
        int damageBlocked = 0;
        if(position == 1){
            damageBlocked += (int) (damage * 0.2);
        }
        damageBlocked += (int) ((damage * armor));
        HumanGameStatics humanStats = GameStatistics.getHumanStats(position);
        assert humanStats != null;
        humanStats.setDamageBlocked(humanStats.getDamageBlocked() + damageBlocked);

        int damageTaken = damage - damageBlocked;
        humanStats.setDamageTaken(humanStats.getDamageTaken() + damageTaken);

        this.health -= damageTaken;

        System.out.println("[ " + this.name + " ] hit: " + damageTaken);
        System.out.println("[ " + this.name + "] health: " + health);

        return damageTaken;
    }

    @Override
    public int heal() {
        if(!alive) return 0;
        HumanGameStatics humanStats = GameStatistics.getHumanStats(position);
        assert humanStats != null;
        humanStats.setLifeHealed(humanStats.getLifeHealed() + healing);
        health += healing;
        int currentHealing = healing;
        healing -= (int) (healing * 0.6);
        if(currentHealing == 1) healing = 0;
        return currentHealing;
    }

    @Override
    public boolean isAlive() {
        return this.health > 0;
    }

    public void die() {
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

        public HumanUnitBuilder setIsAlive(boolean isAlive) {
            this.isAlive = isAlive;
            return this;
        }

        public HumanUnit build() {
            return new HumanUnit(this);
        }

    }
}
