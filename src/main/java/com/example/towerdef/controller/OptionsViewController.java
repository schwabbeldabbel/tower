package com.example.towerdef.controller;

import com.example.towerdef.controller.scenes.SceneController;
import com.example.towerdef.controller.scenes.SceneNames;
import javafx.fxml.FXML;

public class OptionsViewController {


    @FXML
    public void backToStart(){
        SceneController sceneController = SceneController.getInstance();
        sceneController.activate(SceneNames.MAIN);
    }

}
