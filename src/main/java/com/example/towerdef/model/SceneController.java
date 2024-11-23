package com.example.towerdef.model;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.HashMap;

public class SceneController {

    //Singelton pattern to avoid having to handle with different objects
    public static SceneController INSTANCE;

    private HashMap<String, Pane> screenMap = new HashMap<>();
    private HashMap<String, Object> controllerMap = new HashMap<>();
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

    public void addScreen(String name, FXMLLoader loader) throws IOException {
        screenMap.put(name, loader.load());
        controllerMap.put(name, loader.getController());
    }

    public void removeScreen(String name){
        screenMap.remove(name);
    }

    public void activate(String name){
        main.setRoot(screenMap.get(name));
    }

    public Object getController(String name){
        return controllerMap.get(name);
    }

}
