package com.example.towerdef.model.data.human;

import javafx.scene.paint.Color;
import lombok.Getter;


public enum HumanUnitName {

    TANK("Verteidiger", "human-tank", Color.GREEN),
    SNIPER("Scharfschütze", "human-sniper", Color.YELLOW),
    ENGINEER("Mechaniker", "human-engineer", Color.BLUE),
    NONE("Nicht besetzt", "", Color.WHITE);

    @Getter
    private final String name;
    @Getter
    private final String css;
    @Getter
    private final Color color;

    HumanUnitName(String name, String css, Color color) {
        this.name = name;
        this.css = css;
        this.color = color;
    }

}
