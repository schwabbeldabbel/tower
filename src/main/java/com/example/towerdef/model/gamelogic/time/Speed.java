package com.example.towerdef.model.gamelogic.time;

import lombok.Getter;

public enum Speed {
    SLOW(20),
    NORMAL(10),
    FAST(5);

    @Getter
    private int miliseconds;

    private Speed(int miliseconds){
        this.miliseconds = miliseconds;
    }
}
