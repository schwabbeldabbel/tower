package com.example.towerdef.model.gamelogic.setup;

import com.example.towerdef.model.data.tower.Tower;
import com.example.towerdef.model.data.human.HumanUnit;
import com.example.towerdef.model.data.human.HumanUnitName;
import com.example.towerdef.model.data.weapon.Weapon;
import com.example.towerdef.model.data.weapon.WeaponName;
import com.example.towerdef.model.data.weapon.fxmlelement.BulletType;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Singleton
 */
public class GameSettings {

    private static GameSettings INSTANCE;
    private final boolean manualSetUp;

    @Getter
    private int baseHealthHuman;
    @Getter
    private int baseHealthTower;
    private WeaponName towerWeapon;

    //Human weapons
    private final Weapon LMG = new Weapon(WeaponName.LMG, 20, 10, BulletType.NORMAL);
    private final Weapon SNIPER_WEAPON = new Weapon(WeaponName.SNIPER, 100, 40, BulletType.BIG);
    private final Weapon DRILL_CANON = new Weapon(WeaponName.DRILL_CANON, 120, 20, BulletType.DRILL);

    //Human units available
    private List<HumanUnit> humanUnits = new ArrayList<>();

    //Tower weapons
    private final Weapon HANDGUN = new Weapon(WeaponName.HANDGUN, 100, 50, BulletType.BIG);
    private final Weapon MINIGUN = new Weapon(WeaponName.MINIGUN, 10, 2, BulletType.MINI);
    private final Weapon LASER = new Weapon(WeaponName.LASER, 240, 200, BulletType.LASER);

    //Tower
    private Tower tower;

    /**
     *
     * @param baseHealth cannot be null
     * @param humanUnitNames represents the human units and there position based on the array index (top to bottom)
     * @param towerWeapon cannot be null
     */
    private GameSettings(int baseHealth, int baseHealthTower, HumanUnitName[] humanUnitNames, WeaponName towerWeapon, boolean manualSetUp){
        this.manualSetUp = manualSetUp;
        this.baseHealthHuman = baseHealth;
        this.baseHealthTower = baseHealthTower;
        this.towerWeapon = towerWeapon;
        createHumans(humanUnitNames);

        this.tower = new Tower(TowerNameService.getRandomTowerName(), baseHealthTower, getTowerWeapon(towerWeapon), 0.2f);
    }

    /**
     *
     * @param baseHealthHuman base tower health
     * @param baseHealthTower base human health
     * @param humanUnitNames HumanUnitName enum array
     * @param towerWeapon WeaponName enum
     * @return new instance of GameSettings, if not present yet
     */
    public static GameSettings getInstance(int baseHealthHuman, int baseHealthTower, HumanUnitName[] humanUnitNames, WeaponName towerWeapon){
        if(INSTANCE == null){
            INSTANCE = new GameSettings(baseHealthHuman, baseHealthTower, humanUnitNames, towerWeapon, true);
        }
        return INSTANCE;
    }

    public static GameSettings getInstance(){
        if(INSTANCE == null){
            INSTANCE = new GameSettings(
                    100,
                    1500,
                    new HumanUnitName[]{HumanUnitName.ENGINEER, HumanUnitName.SNIPER, HumanUnitName.TANK},
                    WeaponName.MINIGUN,
                    false);
        }
        return INSTANCE;
    }

    public static void removeInstance(){
        INSTANCE = null;
    }

    public Tower getTower(){
        this.tower = new Tower(TowerNameService.getRandomTowerName(), baseHealthTower, getTowerWeapon(towerWeapon), 0.2f);
        return tower;
    }

    public List<HumanUnit> getHumanUnits(){
        HumanUnitName[] names = humanUnits.stream()
                .map(HumanUnit::getName)
                .toArray(HumanUnitName[]::new);
        this.humanUnits = new ArrayList<>();
        createHumans(names);
        return humanUnits;
    }

    private void createHumans(HumanUnitName[] humanUnitNames){
        for(int i = 0; i < humanUnitNames.length; i++){
            switch (humanUnitNames[i]){
                case TANK:
                    addTank(i);
                    break;
                case SNIPER:
                    addSniper(i);
                    break;
                case ENGINEER:
                    addEngineer(i);
                    break;
                default:
                    addNone(i);
                    break;
            }
        }
    }

    private void addTank(int position){
        HumanUnit tank = new HumanUnit.HumanUnitBuilder()
                .setName(HumanUnitName.TANK)
                .setHealth((int) (baseHealthHuman * 1.5))
                .setWeapon(LMG)
                .setHealing((int) (baseHealthHuman * 0.2))
                .setArmor(0.15f)
                .setPosition(position)
                .build();
        this.humanUnits.add(tank);
    }

    private void addSniper(int position){
        HumanUnit sniper = new HumanUnit.HumanUnitBuilder()
                .setName(HumanUnitName.SNIPER)
                .setHealth((int) (baseHealthHuman * 0.8))
                .setWeapon(SNIPER_WEAPON)
                .setHealing((int) (baseHealthHuman * 0.2))
                .setArmor(0.05f)
                .setPosition(position)
                .build();
        this.humanUnits.add(sniper);
    }

    private void addEngineer(int position){
        HumanUnit engineer = new HumanUnit.HumanUnitBuilder()
                .setName(HumanUnitName.ENGINEER)
                .setHealth(baseHealthHuman)
                .setWeapon(DRILL_CANON)
                .setHealing((int) (baseHealthHuman * 0.2))
                .setArmor(0.10f)
                .setPosition(position)
                .build();
        this.humanUnits.add(engineer);
    }

    private void addNone(int position){
        HumanUnit engineer = new HumanUnit.HumanUnitBuilder()
                .setName(HumanUnitName.NONE)
                .setHealth(0)
                .setWeapon(null)
                .setHealing(0)
                .setArmor(0)
                .setPosition(position)
                .setIsAlive(false)
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
