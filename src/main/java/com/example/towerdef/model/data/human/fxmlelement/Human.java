package com.example.towerdef.model.data.human.fxmlelement;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Human extends Pane {
    private Rectangle rectangle;
    private ImageView imageView;

    public Human(double width, double height, Color color) {
        this.rectangle = new Rectangle(width, height, color);
        this.imageView = new ImageView();
        this.imageView.setFitWidth(width);
        this.imageView.setFitHeight(height);
        this.getChildren().addAll(rectangle, imageView);
    }

    public void setImage(String imagePath) {
        Image image = new Image(imagePath);
        this.imageView.setImage(image);
    }

    public void setWidth(double value) {
        rectangle.setWidth(value);
        imageView.setFitWidth(value);
    }

    public void setHeight(double value) {
        rectangle.setHeight(value);
        imageView.setFitHeight(value);
    }
}