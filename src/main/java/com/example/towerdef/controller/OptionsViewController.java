package com.example.towerdef.controller;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import lombok.Setter;

public class OptionsViewController {

    @FXML
    private GridPane mainGridPane;
    @FXML
    private ColumnConstraints col1, col2, col3, col4, col5, col6, col7, col8;
    @FXML
    private RowConstraints row1, row2, row3, row4, row5, row6;

    @Setter
    private int baseSize = 100;

    public void initialize() {
        setConstraintsStyleClass(col1, col2, col3, col4, col5, col6, col7, col8);
        setConstraintsStyleClass(row1, row2, row3, row4, row5, row6);
    }

    private void setConstraintsStyleClass(ColumnConstraints... constraints) {
        for (ColumnConstraints constraint : constraints) {
            constraint.setMinWidth(baseSize);
        }
    }

    private void setConstraintsStyleClass(RowConstraints... constraints) {
        for (RowConstraints constraint : constraints) {
            constraint.setMinHeight(baseSize);
        }
    }

}
