package com.example.towerdef.controller;

import com.example.towerdef.model.data.human.HumanUnit;
import com.example.towerdef.model.data.human.HumanUnitName;
import com.example.towerdef.model.gamelogic.setup.GameSettings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;

public class StatsViewController {
    @FXML
    private VBox humansPlayedContainer;

    public void initialize(){
        GameSettings gameSettings = GameSettings.getInstance();

        initHumansPlayed(gameSettings.getHumanUnits());

    }

    private void initHumansPlayed(List<HumanUnit> humans){
        for(HumanUnit humanUnit: humans){
            if(!humanUnit.getName().equals(HumanUnitName.NONE)){
                HBox unitContainer = new HBox();
                Label humanName = new Label(humanUnit.getName().getName());
                humanName.getStyleClass().add("h2");

                VBox stats = new VBox();

                Label humanDamageDealt = new Label("Schaden gefeuert: " + humanUnit.getDamageFired());
                Label humanDamageTaken = new Label("Schaden erhalten: " + humanUnit.getDamageTaken());
                Label humanDamageBlocked = new Label("Schaden abgewehrt: " + humanUnit.getDamageBlocked());

                stats.getChildren().add(humanDamageDealt);
                stats.getChildren().add(humanDamageTaken);
                stats.getChildren().add(humanDamageBlocked);

                unitContainer.getChildren().add(humanName);
                unitContainer.getChildren().add(stats);

                humansPlayedContainer.getChildren().add(unitContainer);
            }
        }

    }
}
