package com.example.towerdef.model.gamelogic.runtime;


import com.example.towerdef.model.data.Hittable;
import com.example.towerdef.model.data.human.HumanUnit;
import javafx.scene.Node;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class RandomSelector {

    private static final Random random = new Random();

    private RandomSelector() {}

    public static Node updateSelectedHumanTarget(List<HumanUnit> humans, Map<Hittable, Node> hittableNode) {
        List<HumanUnit> foundHumans = humans.stream().filter(HumanUnit::isAlive).toList();
        if(!foundHumans.isEmpty()){
            HumanUnit human = foundHumans.get(random.nextInt(0, foundHumans.size()));
            return hittableNode.get(human);
        }
        return null;
    }

    public static boolean isTowerMalfunction(int value){
        double lambda = 1.0 / (value/100f);
        double probability = 1 - Math.exp(-lambda);
        double randomValue = random.nextDouble();
        return randomValue < probability;
    }

    public static int getRandomDifToGiven(int given, int bound){
        if(given <= 0) given = 1;
        return random.nextInt(given, bound);
    }

}
