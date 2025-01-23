package com.example.towerdef.model.gamelogic.review;


import com.example.towerdef.model.data.human.HumanUnit;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class GameStatistics {


    @Getter@Setter
    private static Winner winner;
    @Getter@Setter
    private static int timePassed;
    @Getter@Setter
    private static List<HumanUnit> humansAlive;


    public static int towerDamageFired;
    public static int towerDamageTaken;
    public static int towerDamageBlocked;
    public static int powerBulletDamage;
    public static int malfunctionDamage;

   public static List<HumanGameStatics> humanGameStatics = new ArrayList<>();

    public static int getData(TowerStatsName statsName){
        switch (statsName){
            case DAMAGE_DEALT -> {
                return towerDamageFired;
            }
            case DAMAGE_TAKEN -> {
                return towerDamageTaken;
            }
            case DAMAGE_BLOCKED -> {
                return towerDamageBlocked;
            }
            case MALFUNCTION_DAMAGE -> {
                return malfunctionDamage;
            }
        }
        return -1;
    }

    public static int getData(HumanStatsName humanStatsName, int humanPosition) {
        HumanGameStatics humanGameStatics = getHumanStats(humanPosition);
        if(humanGameStatics == null) return -1;
        switch (humanStatsName) {
            case DAMAGE_DEALT -> {
                return humanGameStatics.getDamageFired();
            }
            case DAMAGE_TAKEN -> {
                return humanGameStatics.getDamageTaken();
            }
            case DAMAGE_BLOCKED -> {
                return humanGameStatics.getDamageBlocked();
            }
            case LIFE_HEALED -> {
                return humanGameStatics.getLifeHealed();
            }
        }
        return -1;
    }

    public static HumanGameStatics getHumanStats(int humanPosition) {
        for(HumanGameStatics humanGameStatics: humanGameStatics){
            if(humanGameStatics.getPosition() == humanPosition){
                return humanGameStatics;
            }
        }
        return null;
    }

    public static void resetData(List<HumanUnit> humans){
        towerDamageFired = 0;
        towerDamageTaken = 0;
        towerDamageBlocked = 0;
        powerBulletDamage = 0;
        malfunctionDamage = 0;
        resetHumanStats(humans);
    }

    private static void resetHumanStats(List<HumanUnit> humans) {
        humanGameStatics.clear();
        for(HumanUnit human: humans){
            HumanGameStatics humanGameStatics = new HumanGameStatics(human.getPosition());
            GameStatistics.humanGameStatics.add(humanGameStatics);
        }
    }

}
