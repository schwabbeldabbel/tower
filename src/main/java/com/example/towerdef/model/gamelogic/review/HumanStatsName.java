package com.example.towerdef.model.gamelogic.review;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

public enum HumanStatsName {

    DAMAGE_DEALT("Schaden zugefügt"),
    DAMAGE_TAKEN("Schaden erlitten"),
    DAMAGE_BLOCKED("Schaden geblockt"),
    LIFE_HEALED("Leben geheilt");

    @Getter
    private String frontendName;

    private HumanStatsName(String frontendName){
        this.frontendName = frontendName;
    }

    public static HumanStatsName get(String input){
        for(HumanStatsName statsName: HumanStatsName.values()){
            if(statsName.getFrontendName().equals(input)){
                return statsName;
            }
        }
        return null;
    }

    public static List<String> getValueList(){
        HumanStatsName[] humanStatsNames = values();
        return Arrays.stream(humanStatsNames).map(HumanStatsName::getFrontendName).toList();
    }
}
