package com.example.towerdef.model.gamelogic.setup;

import com.example.towerdef.model.data.tower.Tower;
import com.example.towerdef.model.data.human.HumanUnit;
import com.example.towerdef.model.data.human.HumanUnitName;
import com.example.towerdef.model.data.weapon.Weapon;
import com.example.towerdef.model.data.weapon.WeaponName;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class GameSettings {

    private int baseHealth;

    //Human weapons
    private final Weapon LMG = new Weapon(WeaponName.LMG.name(), 1.2F, 10);
    private final Weapon SNIPER_WEAPON = new Weapon(WeaponName.SNIPER.name(), 0.6F, 40);
    private final Weapon DRILL_CANON = new Weapon(WeaponName.DRILL_CANON.name(), 1F, 25);

    //Human units available
    @Getter
    private List<HumanUnit> humanUnits = new ArrayList<>();

    //Tower weapons
    private final Weapon HANDGUN = new Weapon(WeaponName.HANDGUN.name(), 0.8F, 80);
    private final Weapon MINIGUN = new Weapon(WeaponName.MINIGUN.name(), 1.8F, 5);
    private final Weapon LASER = new Weapon(WeaponName.LASER.name(), 2F, 2);

    //Tower
    @Getter
    private Tower tower;

    /**
     *
     * @param baseHealth cannot be null
     * @param tank can be null if not present in game run
     * @param sniper can be null if not present in game run
     * @param engineer can be null if not present in game run
     * @param towerWeapon cannot be null
     */
    public GameSettings(int baseHealth, Integer tank, Integer sniper, Integer engineer, WeaponName towerWeapon){
        this.baseHealth = baseHealth;

        if(tank != null){
            addTank(tank);
        }
        if(sniper != null){
            addSniper(sniper);
        }
        if(engineer != null){
            addEngineer(engineer);
        }

        this.tower = new Tower(TowerNameService.getRandomTowerName(), baseHealth * 2, getTowerWeapon(towerWeapon));
    }


    private void addTank(int position){
        HumanUnit tank = new HumanUnit.HumanUnitBuilder()
                .setName(HumanUnitName.TANK.name())
                .setHealth((int) (baseHealth * 1.5))
                .setWeapon(LMG)
                .setHealing((int) (baseHealth * 0.2))
                .setArmor(0.8F)
                .setPosition(position)
                .build();
        this.humanUnits.add(tank);
    }

    private void addSniper(int position){
        HumanUnit sniper = new HumanUnit.HumanUnitBuilder()
                .setName(HumanUnitName.SNIPER.name())
                .setHealth((int) (baseHealth * 0.8))
                .setWeapon(SNIPER_WEAPON)
                .setHealing((int) (baseHealth * 0.2))
                .setArmor(0.8F)
                .setPosition(position)
                .build();
        this.humanUnits.add(sniper);
    }

    private void addEngineer(int position){
        HumanUnit engineer = new HumanUnit.HumanUnitBuilder()
                .setName(HumanUnitName.ENGINEER.name())
                .setHealth(baseHealth)
                .setWeapon(DRILL_CANON)
                .setHealing((int) (baseHealth * 0.2))
                .setArmor(0.8F)
                .setPosition(position)
                .build();
        this.humanUnits.add(engineer);
    }


    private Weapon getTowerWeapon(WeaponName name){
        switch (name) {
            case HANDGUN -> {
                return this.HANDGUN;
            }
            case MINIGUN -> {
                return this.MINIGUN;
            }
            case LASER -> {
                return this.LASER;
            }
            default -> {
                return this.MINIGUN;
            }
        }
    }

}
