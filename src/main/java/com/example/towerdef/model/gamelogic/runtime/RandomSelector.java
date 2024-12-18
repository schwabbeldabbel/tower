package com.example.towerdef.model.gamelogic.runtime;


import com.example.towerdef.model.data.Hittable;
import com.example.towerdef.model.data.human.HumanUnit;
import javafx.scene.Node;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class RandomSelector {

    private static final Random random = new Random();

    private RandomSelector() {
    }

    public static Node updateSelectedHumanTarget(List<HumanUnit> humans, Map<Hittable, Node> hittableNode) {
        List<HumanUnit> foundHumans = humans.stream().filter(HumanUnit::isAlive).toList();
        if (!foundHumans.isEmpty()) {
            HumanUnit human = foundHumans.get(random.nextInt(0, foundHumans.size()));
            return hittableNode.get(human);
        }
        return null;
    }

    public static boolean isTowerMalfunction(int value) {
        double lambda = 1.0 / (value / 35f);
        double probability = 1 - Math.exp(-lambda);
        double randomValue = random.nextDouble();
        return randomValue < probability;
    }

    public static int isTowerOverdrive(int value, int max) {
        double probability = calculateProbability(value, max);
        if (random.nextDouble() < probability) {
            return towerOverdriveBullets(value, max);
        }
        return -1;
    }

    private static double calculateProbability(int value, int max) {
        double oneThird = max / 3.0;
        double twoThirds = 2 * max / 3.0;
        double distanceToOneThird = Math.abs(value - oneThird);
        double distanceToTwoThirds = Math.abs(value - twoThirds);
        double minDistance = Math.min(distanceToOneThird, distanceToTwoThirds);
        return 1.0 / (1.0 + minDistance);
    }

    private static int towerOverdriveBullets(int value, int max) {
        double mean = 60 - Math.min(50, (value / max) * 50);
        double stdDev = 10;
        int randomValue;
        do {
            randomValue = (int) (random.nextGaussian() * stdDev + mean);
        } while (randomValue < 10 || randomValue > 50);
        return randomValue;
    }

    public static int getRandomDifToGiven(int given, int bound) {
        if (given <= 0) given = 1;
        return random.nextInt(given, bound);
    }

    public static boolean getIsHealing(int current, int max){
        double probability = (double) current / max;
        double randomValue = random.nextDouble() - 0.5;
        return randomValue > probability;
    }

}
