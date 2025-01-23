package com.example.towerdef.model.gamelogic.review;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

public enum TowerStatsName {

    DAMAGE_DEALT("Schaden zugefügt"),
    DAMAGE_TAKEN("Schaden erlitten"),
    DAMAGE_BLOCKED("Schaden geblockt"),
    MALFUNCTION_DAMAGE("Schaden durch Fehlfunktion");

    @Getter
    private String frontendName;

    private TowerStatsName(String frontendName){
        this.frontendName = frontendName;
    }

    public static TowerStatsName get(String input){
        for(TowerStatsName statsName: TowerStatsName.values()){
            if(statsName.getFrontendName().equals(input)){
                return statsName;
            }
        }
        return null;
    }

    public static List<String> getValueList(){
        TowerStatsName[] humanStatsNames = values();
        return Arrays.stream(humanStatsNames).map(TowerStatsName::getFrontendName).toList();
    }
}
