package com.example.towerdef;

import com.example.towerdef.controller.scenes.SceneController;
import com.example.towerdef.controller.scenes.SceneNames;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;


public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader main = new FXMLLoader(HelloApplication.class.getResource("fxml/start-screen-view.fxml"));

        Scene scene = new Scene(main.load(), 1200, 700);
        SceneController sceneController = SceneController.getInstance(scene);
        sceneController.addScreen(SceneNames.MAIN,"fxml/start-screen-view.fxml");
        sceneController.addScreen(SceneNames.GAME, "fxml/game-view.fxml");
        sceneController.addScreen(SceneNames.OPTIONS, "fxml/options-view.fxml");
        sceneController.addScreen(SceneNames.STATS, "fxml/stats-view.fxml");
        stage.setTitle("Tower!!");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
        sceneController.activate(SceneNames.MAIN);
    }

    public static void main(String[] args) {
        launch();
    }
}