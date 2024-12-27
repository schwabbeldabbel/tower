package com.example.towerdef.model.gamelogic.review;

import lombok.Getter;

public enum Winner {
    HUMANS("Menschen"),
    TOWER("Turm");

    @Getter
    private final String frontendName;

    private Winner(String frontendName){
        this.frontendName = frontendName;
    }
}
