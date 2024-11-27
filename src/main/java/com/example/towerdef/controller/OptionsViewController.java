package com.example.towerdef.controller;

import com.example.towerdef.controller.scenes.SceneController;
import com.example.towerdef.controller.scenes.SceneNames;
import com.example.towerdef.model.data.human.HumanUnitName;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.shape.Rectangle;

import java.awt.event.ActionEvent;


public class OptionsViewController {

    @FXML
    protected Button humanPos1, humanPos2, humanPos3;

    @FXML
    public void backToStart() {
        SceneController sceneController = SceneController.getInstance();
        sceneController.activate(SceneNames.MAIN);
    }

    @FXML
    public void setHuman(Event event) {
        String tankCSS = HumanUnitName.TANK.getCss();
        String sniperCSS = HumanUnitName.SNIPER.getCss();
        String engineerCSS = HumanUnitName.ENGINEER.getCss();

        Button clickedBtn = (Button) event.getSource();
        if (clickedBtn.getStyleClass().size() <= 1) {
            clickedBtn.getStyleClass().add(tankCSS);

        } else {
            String style = clickedBtn.getStyleClass().get(1);

            if (style.equals(tankCSS)) {
                clickedBtn.getStyleClass().remove(tankCSS);
                clickedBtn.getStyleClass().add(sniperCSS);

            } else if (style.equals(sniperCSS)) {
                clickedBtn.getStyleClass().remove(sniperCSS);
                clickedBtn.getStyleClass().add(engineerCSS);

            } else if (style.equals(engineerCSS)) {
                clickedBtn.getStyleClass().remove(engineerCSS);
            }
        }
    }

}
