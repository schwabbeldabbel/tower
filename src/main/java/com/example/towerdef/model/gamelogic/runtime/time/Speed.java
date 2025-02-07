package com.example.towerdef.model.gamelogic.runtime.time;

import lombok.Getter;

public enum Speed {
    SLOW(20),
    NORMAL(10),
    FAST(5);

    @Getter
    private final int milliseconds;

    private Speed(int milliseconds){
        this.milliseconds = milliseconds;
    }
}
