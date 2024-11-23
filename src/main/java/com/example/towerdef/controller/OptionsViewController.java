package com.example.towerdef.controller;

import com.example.towerdef.controller.scenes.SceneController;
import com.example.towerdef.controller.scenes.SceneNames;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.shape.Rectangle;

import java.awt.event.ActionEvent;


public class OptionsViewController {

    @FXML
    protected Button humanPos1, humanPos2, humanPos3;

    @FXML
    public void backToStart(){
        SceneController sceneController = SceneController.getInstance();
        sceneController.activate(SceneNames.MAIN);
    }

    @FXML
    public void setHuman(Event event){
        Button clickedBtn = (Button) event.getSource();
        if(clickedBtn.getStyleClass().size() <= 1){
            clickedBtn.getStyleClass().add("human-tank");
        }else{
            String style = clickedBtn.getStyleClass().get(1);
            if(style.equals("human-tank")) {
                clickedBtn.getStyleClass().remove("human-tank");
                clickedBtn.getStyleClass().add("human-sniper");
            }else if(style.equals("human-sniper")){
                clickedBtn.getStyleClass().remove("human-sniper");
                clickedBtn.getStyleClass().add("human-engineer");
            }else if(style.equals("human-engineer")){
                clickedBtn.getStyleClass().remove("human-engineer");
            }
        }
    }

}
