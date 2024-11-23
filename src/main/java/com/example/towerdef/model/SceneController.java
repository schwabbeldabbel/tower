package com.example.towerdef.model;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.util.HashMap;

public class SceneController {

    //Singelton pattern to avoid having to handle with different objects
    public static SceneController INSTANCE;

    private HashMap<String, Pane> screenMap = new HashMap<>();
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

    public void addScreen(String name, Pane pane){
        screenMap.put(name, pane);
    }

    public void removeScreen(String name){
        screenMap.remove(name);
    }

    public void activate(String name){
        main.setRoot( screenMap.get(name) );
    }

}
