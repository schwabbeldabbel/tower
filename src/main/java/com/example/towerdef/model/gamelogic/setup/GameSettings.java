package com.example.towerdef.model.gamelogic.setup;

import com.example.towerdef.model.data.tower.Tower;
import com.example.towerdef.model.data.human.HumanUnit;
import com.example.towerdef.model.data.human.HumanUnitName;
import com.example.towerdef.model.data.weapon.Weapon;
import com.example.towerdef.model.data.weapon.WeaponName;
import com.example.towerdef.model.data.weapon.fxmlelement.BulletType;
import com.example.towerdef.model.gamelogic.review.GameStatistics;
import com.example.towerdef.model.gamelogic.review.Winner;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

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
    private final Weapon LMG = new Weapon(WeaponName.LMG, 50, 10, BulletType.NORMAL);
    private final Weapon SNIPER_WEAPON = new Weapon(WeaponName.SNIPER, 100, 100, BulletType.FIRE_BALL);
    private final Weapon DRILL_CANON = new Weapon(WeaponName.DRILL_CANON, 120, 150, BulletType.DRILL);

    //Human units available
    @Getter
    private List<HumanUnit> humanUnits = new ArrayList<>();

    //Tower weapons
    private final Weapon HANDGUN = new Weapon(WeaponName.HANDGUN, 100, 50, BulletType.SUPER);
    private final Weapon MINIGUN = new Weapon(WeaponName.MINIGUN, 20, 8, BulletType.MINI);
    private final Weapon LASER = new Weapon(WeaponName.LASER, 240, 150, BulletType.LASER);

    //Tower
    @Getter
    private Tower tower;

    /**
     * @param baseHealth     cannot be null
     * @param humanUnitNames represents the human units and there position based on the array index (top to bottom)
     * @param towerWeapon    cannot be null
     */
    private GameSettings(int baseHealth, int baseHealthTower, HumanUnitName[] humanUnitNames, WeaponName towerWeapon, boolean manualSetUp) {
        this.manualSetUp = manualSetUp;
        this.baseHealthHuman = baseHealth;
        this.baseHealthTower = baseHealthTower;
        this.towerWeapon = towerWeapon;
        createHumans(humanUnitNames);

        this.tower = getNewTower();
    }

    /**
     * @param baseHealthHuman base tower health
     * @param baseHealthTower base human health
     * @param humanUnitNames  HumanUnitName enum array
     * @param towerWeapon     WeaponName enum
     * @return new instance of GameSettings, if not present yet
     */
    public static GameSettings getInstance(int baseHealthHuman, int baseHealthTower, HumanUnitName[] humanUnitNames, WeaponName towerWeapon) {
        if (INSTANCE == null) {
            INSTANCE = new GameSettings(baseHealthHuman, baseHealthTower, humanUnitNames, towerWeapon, true);
        }
        return INSTANCE;
    }

    public static GameSettings getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GameSettings(
                    100,
                    1500,
                    new HumanUnitName[]{HumanUnitName.ENGINEER, HumanUnitName.SNIPER, HumanUnitName.TANK},
                    WeaponName.MINIGUN,
                    false);
        }
        return INSTANCE;
    }

    public static void removeInstance() {
        INSTANCE = null;
    }

    public Tower getNewTower() {
        this.tower = new Tower(TowerNameService.getRandomTowerName(), baseHealthTower, getTowerWeapon(towerWeapon), 0.3f);
        return tower;
    }

    public List<HumanUnit> getNewHumanUnits() {
        HumanUnitName[] names = humanUnits.stream()
                .map(HumanUnit::getName)
                .toArray(HumanUnitName[]::new);
        this.humanUnits = new ArrayList<>();
        createHumans(names);
        return humanUnits;
    }

    public void setGameStatics(int timePassed) {
        setWinner();
        GameStatistics.setTimePassed(timePassed);
        setHumansAlive();
    }

    private void setWinner() {
        boolean humanIsAlive = false;
        for (HumanUnit human : humanUnits) {
            if (human.isAlive()) {
                humanIsAlive = true;
            }
        }
        if (!tower.isAlive() && humanIsAlive) {
            GameStatistics.setWinner(Winner.HUMANS);
        } else {
            GameStatistics.setWinner(Winner.TOWER);
        }
    }

    private void setHumansAlive() {
        GameStatistics.setHumansAlive(humanUnits.stream()
                .filter(HumanUnit::isAlive).toList());
    }

    private void createHumans(HumanUnitName[] humanUnitNames) {
        for (int i = 0; i < humanUnitNames.length; i++) {
            switch (humanUnitNames[i]) {
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

    private void addTank(int position) {
        HumanUnit tank = new HumanUnit.HumanUnitBuilder()
                .setName(HumanUnitName.TANK)
                .setHealth((int) (baseHealthHuman * 2))
                .setWeapon(LMG)
                .setArmor(0.35f)
                .setPosition(position)
                .build();
        this.humanUnits.add(tank);
    }

    private void addSniper(int position) {
        HumanUnit sniper = new HumanUnit.HumanUnitBuilder()
                .setName(HumanUnitName.SNIPER)
                .setHealth((int) (baseHealthHuman * 0.5))
                .setWeapon(SNIPER_WEAPON)
                .setArmor(0.15f)
                .setPosition(position)
                .build();
        this.humanUnits.add(sniper);
    }

    private void addEngineer(int position) {
        HumanUnit engineer = new HumanUnit.HumanUnitBuilder()
                .setName(HumanUnitName.ENGINEER)
                .setHealth(baseHealthHuman)
                .setWeapon(DRILL_CANON)
                .setArmor(0.25f)
                .setPosition(position)
                .build();
        this.humanUnits.add(engineer);
    }

    private void addNone(int position) {
        HumanUnit engineer = new HumanUnit.HumanUnitBuilder()
                .setName(HumanUnitName.NONE)
                .setHealth(0)
                .setWeapon(null)
                .setArmor(0)
                .setPosition(position)
                .setIsAlive(false)
                .build();
        this.humanUnits.add(engineer);
    }

    private Weapon getTowerWeapon(WeaponName name) {
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
