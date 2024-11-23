package com.example.towerdef.controller.scenes;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.util.HashMap;

public class SceneController {

    //Singelton pattern to avoid having to handle with different objects
    public static SceneController INSTANCE;

    private HashMap<SceneNames, Pane> screenMap = new HashMap<>();
    private Scene main;

    private SceneController(Scene main){
        this.main = main;
    }

    public static SceneController getInstance(Scene main) {
        if(INSTANCE == null){
            INSTANCE = new SceneController(main);
        }
        return INSTANCE;
    }
    public static SceneController getInstance() {
        return INSTANCE;
    }

    public void addScreen(SceneNames name, Pane pane) {
        screenMap.put(name, pane);
    }

    public void removeScreen(SceneNames name){
        screenMap.remove(name);
    }

    public void activate(SceneNames name){
        main.setRoot(screenMap.get(name));
    }

}
