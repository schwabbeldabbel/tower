package com.example.towerdef.controller;

import com.example.towerdef.model.SceneController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    protected void startGame(){
        SceneController sceneController = SceneController.getInstance();
        sceneController.activate("game");
    }
}