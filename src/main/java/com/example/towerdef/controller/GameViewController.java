package com.example.towerdef.controller;

import com.example.towerdef.model.gamelogic.setup.GameSettings;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;
import lombok.Setter;

public class GameViewController {

    @FXML private Rectangle humanPos1;
    @FXML private Rectangle humanPos2;
    @FXML private Rectangle humanPos3;

    private GameSettings gameSettings;

    @Setter
    private int baseSize = 100;

    public void initialize(){
        this.gameSettings = GameSettings.getInstance();
    }

    private void adjustSize(Rectangle... nodes){
        for(Rectangle node: nodes){
            node.setWidth(baseSize);
            node.setHeight(baseSize);
        }
    }

}
