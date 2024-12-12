package com.example.towerdef.model.gamelogic.runtime;

import com.example.towerdef.model.data.human.HumanUnit;
import com.example.towerdef.model.data.weapon.Weapon;
import com.example.towerdef.model.data.weapon.fxmlelement.Bullet;
import org.uncommons.maths.random.ExponentialGenerator;
import org.uncommons.maths.random.MersenneTwisterRNG;

import java.util.Random;

public class RandomSelector {

    public static int updateSelectedHumanTarget(int bound) {
        Random random = new Random();
        return random.nextInt(1, bound + 1);
    }

    public static boolean isTowerMalfunction(int value){
        MersenneTwisterRNG rng = new MersenneTwisterRNG();
        double lambda = 1.0 / value;
        ExponentialGenerator exponentialGenerator = new ExponentialGenerator(lambda, rng);
        double probability = exponentialGenerator.nextValue();
        double randomValue = rng.nextDouble();
        return randomValue < probability;
    }

}
