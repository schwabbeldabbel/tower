package com.example.towerdef;

import com.example.towerdef.model.SceneController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader main = new FXMLLoader(HelloApplication.class.getResource("fxml/start-screen-view.fxml"));
        FXMLLoader options = new FXMLLoader(HelloApplication.class.getResource("fxml/game-view.fxml"));
        FXMLLoader game = new FXMLLoader(HelloApplication.class.getResource("fxml/options-view.fxml"));

        Scene scene = new Scene(main.load(), 1000, 700);
        SceneController sceneController = SceneController.getInstance(scene);
        sceneController.addScreen("main", main);
        sceneController.addScreen("options", options);
        sceneController.addScreen("game", game);
        stage.setTitle("Tower!!");
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}