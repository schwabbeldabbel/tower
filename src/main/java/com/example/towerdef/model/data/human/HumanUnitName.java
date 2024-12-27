package com.example.towerdef.model.data.human;

import javafx.scene.paint.Color;
import lombok.Getter;


public enum HumanUnitName {

    TANK("Verteidiger", "human-tank", Color.GREEN),
    SNIPER("Feuerteufel", "human-sniper", Color.YELLOW),
    ENGINEER("Mechaniker", "human-engineer", Color.BLUE),
    NONE("Nicht besetzt", "none", Color.WHITE);

    @Getter
    private final String frontendName;
    @Getter
    private final String css;
    @Getter
    private final Color color;

    HumanUnitName(String frontendName, String css, Color color) {
        this.frontendName = frontendName;
        this.css = css;
        this.color = color;
    }

}
