package com.example.towerdef.controller.scenes;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public class SceneController {

    //Singelton pattern to avoid having to handle with different objects
    public static SceneController INSTANCE;

    private HashMap<SceneNames, String> screenMap = new HashMap<>();
    private Scene main;

    private SceneController(Scene main) {
        this.main = main;
    }

    public static SceneController getInstance(Scene main) {
        if (INSTANCE == null) {
            INSTANCE = new SceneController(main);
        }
        return INSTANCE;
    }

    public static SceneController getInstance() {
        return INSTANCE;
    }

    public void addScreen(SceneNames name, String fxmlFile) {
        screenMap.put(name, fxmlFile);
    }

    public void removeScreen(SceneNames name) {
        screenMap.remove(name);
    }

    public void activate(SceneNames name) {
        try {
            String fxmlFile = screenMap.get(name);
            if (fxmlFile != null) {                                 //src/main/resources/com/example/towerdef/fxml/start-screen-view.fxml
                Pane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/towerdef/" + fxmlFile)));
                main.setRoot(pane);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
