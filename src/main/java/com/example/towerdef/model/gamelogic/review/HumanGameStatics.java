package com.example.towerdef.model.gamelogic.review;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter@Setter@RequiredArgsConstructor
public class HumanGameStatics {

    private int damageFired = 0;
    private int damageTaken = 0;
    private int damageBlocked = 0;
    private int lifeHealed = 0;
    @NonNull
    private int position;

}
