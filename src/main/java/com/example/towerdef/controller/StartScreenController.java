package com.example.towerdef.controller;

import com.example.towerdef.model.SceneController;
import javafx.fxml.FXML;

public class StartScreenController {


    @FXML
    protected void startGame(){
        SceneController sceneController = SceneController.getInstance();
        sceneController.activate("game");
    }
    @FXML
    protected void startOptions(){
        SceneController sceneController = SceneController.getInstance();
        OptionsViewController opt = (OptionsViewController) sceneController.getController("options");
        opt.setBaseSize(1000);
        sceneController.activate("options");
    }
}