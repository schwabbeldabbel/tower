package com.example.towerdef.controller;

import com.example.towerdef.controller.scenes.SceneController;
import com.example.towerdef.controller.scenes.SceneNames;
import com.example.towerdef.model.data.human.HumanUnit;
import com.example.towerdef.model.data.human.HumanUnitName;
import com.example.towerdef.model.data.tower.Tower;
import com.example.towerdef.model.gamelogic.review.GameStatistics;
import com.example.towerdef.model.gamelogic.review.HumanStatsName;
import com.example.towerdef.model.gamelogic.review.TowerStatsName;
import com.example.towerdef.model.gamelogic.setup.GameSettings;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;

import java.util.*;
//TODO allgemeine Daten anzeigen: wie viele Sekunden? wer ist am leben? Wer hat gewonnen?
public class StatsViewController {
    @FXML
    private BorderPane humansPlayedContainer, towerChart;

    private MenuButton humanStatsSelector, towerStatsSelector;


    private Set<HumanStatsName> selectedHumanStats;
    private Set<TowerStatsName> selectedTowerStats;

    private GameSettings gameSettings;

    public void initialize() {
        gameSettings = GameSettings.getInstance();
        selectedHumanStats = new HashSet<>();
        selectedTowerStats = new HashSet<>();
        selectedHumanStats.add(HumanStatsName.DAMAGE_DEALT);
        selectedTowerStats.add(TowerStatsName.DAMAGE_DEALT);
        initComboBox();
        if(GameStatistics.humanGameStatics != null && !GameStatistics.humanGameStatics.isEmpty()) {
            setUpCharts();
        }else{
            setUpNoData();
        }
    }

    private void setUpNoData(){
        Label humanLabel = new Label("Starte deinen ersten Durchlauf, damit hier die Statistik angezeigt werden kann.");
        Label towerLabel = new Label(humanLabel.getText());
        humansPlayedContainer.setCenter(humanLabel);
        towerChart.setCenter(towerLabel);
    }

    private void setUpCharts(){
        initHumansPlayed(gameSettings.getHumanUnits());
        initTower(gameSettings.getTower());
    }

    private void initComboBox(){
        humanStatsSelector = new MenuButton("Wähle die Details zum Vergleichen");
        towerStatsSelector = new MenuButton("Wähle die Details zum Vergleichen");

        List<CheckMenuItem> humanMenuItems = HumanStatsName.getValueList().stream().map(CheckMenuItem::new).toList();
        humanStatsSelector.getItems().addAll(humanMenuItems);

        for (final CheckMenuItem item : humanMenuItems) {
            if(item.getText().equals(HumanStatsName.DAMAGE_DEALT.getFrontendName())){
                item.setSelected(true);
            }
            item.selectedProperty().addListener((observableValue, oldValue, newValue) -> {
                if (newValue) {
                    selectedHumanStats.add(HumanStatsName.get(item.getText()));
                } else {
                    selectedHumanStats.remove(HumanStatsName.get(item.getText()));
                }
                setUpCharts();
            });
        }

        List<CheckMenuItem> towerMenuItems = TowerStatsName.getValueList().stream().map(CheckMenuItem::new).toList();
        towerStatsSelector.getItems().addAll(towerMenuItems);

        for (final CheckMenuItem item : towerMenuItems) {
            if(item.getText().equals(HumanStatsName.DAMAGE_DEALT.getFrontendName())){
                item.setSelected(true);
            }
            item.selectedProperty().addListener((observableValue, oldValue, newValue) -> {
                if (newValue) {
                    selectedTowerStats.add(TowerStatsName.get(item.getText()));
                } else {
                    selectedTowerStats.remove(TowerStatsName.get(item.getText()));
                }
                setUpCharts();
            });
        }
    }

    private void initHumansPlayed(List<HumanUnit> humans) {
        CategoryAxis yAxis = new CategoryAxis();
        NumberAxis xAxis = new NumberAxis();
        xAxis.getStyleClass().add("axis");
        yAxis.getStyleClass().add("axis");

        yAxis.setCategories(FXCollections.<String>observableArrayList(selectedHumanStats.stream()
                .map(HumanStatsName::getFrontendName)
                .toList()));

        xAxis.setLabel("Wert");

        BarChart<Number, String> barChartHuman = new BarChart<>(xAxis, yAxis);

        barChartHuman.setTitle("Auswertung der Menschen");

        for (HumanUnit human : humans) {
            if(!human.getName().equals(HumanUnitName.NONE)){
                XYChart.Series<Number, String> data = new XYChart.Series<>();
                data.setName(human.getName().getName());
                for (HumanStatsName humanStatsName : selectedHumanStats) {
                    data.getData().add(new XYChart.Data<>(GameStatistics.getData(humanStatsName, human.getPosition()), humanStatsName.getFrontendName()));
                }
                barChartHuman.getData().add(data);
            }
        }

        for (final XYChart.Series<Number, String> series : barChartHuman.getData()) {
            for (final XYChart.Data<Number, String> data : series.getData()) {
                Tooltip tooltip = new Tooltip();
                tooltip.setText(data.getXValue().toString() + " " +
                        data.getYValue().toString());
                Tooltip.install(data.getNode(), tooltip);
            }
        }
        humansPlayedContainer.getChildren().removeAll();
        humansPlayedContainer.setTop(humanStatsSelector);
        humansPlayedContainer.setCenter(barChartHuman);

    }

    private void initTower(Tower tower) {
        CategoryAxis yAxis = new CategoryAxis();
        NumberAxis xAxis = new NumberAxis();
        xAxis.getStyleClass().add("axis");
        yAxis.getStyleClass().add("axis");
        yAxis.setCategories(FXCollections.<String>observableArrayList(selectedTowerStats.stream()
                .map(TowerStatsName::getFrontendName)
                .toList()));
        xAxis.setLabel("Wert");

        BarChart<Number, String> barchartTower = new BarChart<>(xAxis, yAxis);

        barchartTower.setTitle("Auswertung des Turms");
        barchartTower.setBarGap(3);
        barchartTower.setCategoryGap(20);

        XYChart.Series<Number, String> data = new XYChart.Series<>();
        data.setName(tower.getName());
        for (TowerStatsName towerStatsName : selectedTowerStats) {
            data.getData().add(new XYChart.Data<>(GameStatistics.getData(towerStatsName), towerStatsName.getFrontendName()));
        }
        barchartTower.getData().add(data);

        for (final XYChart.Series<Number, String> series : barchartTower.getData()) {
            for (final XYChart.Data<Number, String> towerData : series.getData()) {
                Tooltip tooltip = new Tooltip();
                tooltip.setText(towerData.getXValue().toString() + " " +
                        towerData.getYValue().toString());
                Tooltip.install(towerData.getNode(), tooltip);
            }
        }
        towerChart.getChildren().removeAll();
        towerChart.setTop(towerStatsSelector);
        towerChart.setCenter(barchartTower);
    }

    @FXML
    protected void toStart(){
        SceneController.getInstance().activate(SceneNames.MAIN);
    }
    @FXML
    protected void toOptions(){
        SceneController.getInstance().activate(SceneNames.OPTIONS);
    }
    @FXML
    protected void replay(){
        SceneController.getInstance().activate(SceneNames.GAME);
    }
    @FXML
    protected void endProgramm(){
        System.exit(200);
    }
}
