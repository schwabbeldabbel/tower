package com.example.towerdef.model.gamelogic.runtime;

import com.example.towerdef.model.data.human.HumanUnit;
import com.example.towerdef.model.data.tower.Tower;
import com.example.towerdef.model.data.weapon.Weapon;
import com.example.towerdef.model.data.weapon.fxmlelement.Bullet;

import java.util.List;

public class GameplayTimer {

    public boolean checkHumanShooting(int milliSeconds, HumanUnit human) {
        Weapon weapon = human.getWeapon();
        if (weapon != null) {
            return milliSeconds % weapon.getAttackSpeed() == 0;
        }
        return false;
    }

    public boolean checkTowerShooting(int milliSeconds, Tower tower) {
        return milliSeconds % tower.getWeapon().getAttackSpeed() == 0;
    }

}
