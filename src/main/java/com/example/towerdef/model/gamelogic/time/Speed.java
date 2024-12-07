package com.example.towerdef.model.gamelogic.time;

import lombok.Getter;

public enum Speed {
    SLOW(2),
    NORMAL(1),
    FAST(5);

    @Getter
    private int miliseconds;

    private Speed(int miliseconds){
        this.miliseconds = miliseconds;
    }
}
