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

    private static GameSettings INSTANCE;

    private int baseHealthHuman;
    private int baseHealthTower;

    //Human weapons
    private final Weapon LMG = new Weapon(WeaponName.LMG.name(), 5, 10);
    private final Weapon SNIPER_WEAPON = new Weapon(WeaponName.SNIPER.name(), 1, 40);
    private final Weapon DRILL_CANON = new Weapon(WeaponName.DRILL_CANON.name(), 2, 25);

    //Human units available
    @Getter
    private List<HumanUnit> humanUnits = new ArrayList<>();

    //Tower weapons
    private final Weapon HANDGUN = new Weapon(WeaponName.HANDGUN.name(), 1, 80);
    private final Weapon MINIGUN = new Weapon(WeaponName.MINIGUN.name(), 5, 5);
    private final Weapon LASER = new Weapon(WeaponName.LASER.name(), 10, 2);

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
    private GameSettings(int baseHealth, int baseHealthTower, Integer tank, Integer sniper, Integer engineer, WeaponName towerWeapon){
        this.baseHealthHuman = baseHealth;

        if(tank != null){
            addTank(tank);
        }
        if(sniper != null){
            addSniper(sniper);
        }
        if(engineer != null){
            addEngineer(engineer);
        }

        this.tower = new Tower(TowerNameService.getRandomTowerName(), baseHealthTower, getTowerWeapon(towerWeapon));
    }

    public static GameSettings getInstance(int baseHealthHuman, int baseHealthTower, Integer tank, Integer sniper, Integer engineer, WeaponName towerWeapon){
        if(INSTANCE == null){
            INSTANCE = new GameSettings(baseHealthHuman, baseHealthTower, tank, sniper, engineer, towerWeapon);
        }
        return INSTANCE;
    }

    public static GameSettings getInstance(){
        return INSTANCE;
    }

    private void addTank(int position){
        HumanUnit tank = new HumanUnit.HumanUnitBuilder()
                .setName(HumanUnitName.TANK.name())
                .setHealth((int) (baseHealthHuman * 1.5))
                .setWeapon(LMG)
                .setHealing((int) (baseHealthHuman * 0.2))
                .setArmor(0.8F)
                .setPosition(position)
                .build();
        this.humanUnits.add(tank);
    }

    private void addSniper(int position){
        HumanUnit sniper = new HumanUnit.HumanUnitBuilder()
                .setName(HumanUnitName.SNIPER.name())
                .setHealth((int) (baseHealthHuman * 0.8))
                .setWeapon(SNIPER_WEAPON)
                .setHealing((int) (baseHealthHuman * 0.2))
                .setArmor(0.8F)
                .setPosition(position)
                .build();
        this.humanUnits.add(sniper);
    }

    private void addEngineer(int position){
        HumanUnit engineer = new HumanUnit.HumanUnitBuilder()
                .setName(HumanUnitName.ENGINEER.name())
                .setHealth(baseHealthHuman)
                .setWeapon(DRILL_CANON)
                .setHealing((int) (baseHealthHuman * 0.2))
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
