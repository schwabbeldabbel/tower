package com.example.towerdef.model.gamelogic.runtime;


import java.util.Random;

public class RandomSelector {

    private static final Random random = new Random();

    private RandomSelector() {}

    //TODO the random selection was based on the removing of the humans if dead. But now the human List shouldn´t be edited so the random selection of the human has to be changed.
    public static int updateSelectedHumanTarget(int bound) {
        return random.nextInt(1, bound + 1);
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
