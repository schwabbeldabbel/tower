package com.example.towerdef.controller;

import com.example.towerdef.controller.scenes.SceneController;
import com.example.towerdef.controller.scenes.SceneNames;
import javafx.fxml.FXML;

public class StartScreenController {

    @FXML
    protected void startGame(){
        SceneController sceneController = SceneController.getInstance();
        sceneController.activate(SceneNames.GAME);
    }

    @FXML
    protected void startOptions(){
        SceneController sceneController = SceneController.getInstance();
        sceneController.activate(SceneNames.OPTIONS);
    }
}