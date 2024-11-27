package com.example.towerdef.model.data.human;

import lombok.Getter;

public enum HumanUnitName {

    TANK("human-tank"),
    SNIPER("human-sniper"),
    ENGINEER("human-engineer");

    @Getter
    private String css;

    HumanUnitName(String css){
        this.css = css;
    }

}
