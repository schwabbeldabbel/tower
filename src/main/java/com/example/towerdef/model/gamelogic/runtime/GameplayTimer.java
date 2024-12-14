package com.example.towerdef.model.gamelogic.runtime;

import com.example.towerdef.model.data.human.HumanUnit;
import com.example.towerdef.model.data.tower.Tower;
import com.example.towerdef.model.data.weapon.Weapon;
import com.example.towerdef.model.data.weapon.fxmlelement.Bullet;

import java.util.List;

public class GameplayTimer {

    public void checkShooting(int milliSeconds, List<HumanUnit> humans, Tower tower) {
        for (HumanUnit humanUnit : humans) {
            Weapon weapon = humanUnit.getWeapon();
            if(weapon != null){
                if (milliSeconds % weapon.getAttackSpeed() == 0) {
                    Bullet bullet = humanUnit.shoot();
                    if(bullet != null){
                        addBullet(bullet, humanUnit.getPosition(), towerPos, weapon.getDamage());
                    }
                }
            }
        }
        if (milliSeconds % tower.getWeapon().getAttackSpeed() == 0) {
            Bullet bullet = tower.shoot();
            if(bullet != null) {
                addBullet(bullet, -1, RandomSelector.updateSelectedHumanTarget(humans, hittablePosition), tower.getWeapon().getDamage());
            }
        }
    }

}
