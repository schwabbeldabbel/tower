package com.example.towerdef.model.gamelogic.runtime;

import com.example.towerdef.model.data.human.HumanUnit;
import com.example.towerdef.model.data.weapon.Weapon;
import com.example.towerdef.model.data.weapon.fxmlelement.Bullet;

import java.util.Random;

public class RandomSelector {

    public static int updateSelectedHumanTarget(int bound) {
        Random random = new Random();
        return random.nextInt(0, bound);
    }

}
