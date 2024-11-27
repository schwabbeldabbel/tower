package com.example.towerdef.model.gamelogic.time;

import com.example.towerdef.model.data.weapon.Weapon;
import javafx.application.Platform;
import javafx.scene.control.Label;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class TimerThread implements Runnable {
    @Getter
    private int milliSeconds;
    private volatile boolean running;
    private Thread thread;
    private Label timeLabel;

    private List<Weapon> weapons =  new ArrayList<>();

    @Setter
    private int speed;

    public TimerThread(Label timeLabel, Speed speed, List<Weapon> weapons) {
        this.speed = speed.getMiliseconds();
        this.milliSeconds = 0;
        this.running = false;
        this.timeLabel = timeLabel;
        this.weapons = weapons;
        this.run();
    }

    @Override
    public void run() {
        while (running) {
            try {
                Thread.sleep(speed);
                milliSeconds++;
                checkShooting(milliSeconds);
                Platform.runLater(() -> timeLabel.setText(milliSeconds / 100 + " Sekunden"));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Thread wurde unterbrochen.");
            }
        }
    }

    public void start() {
        if (!running) {
            running = true;
            thread = new Thread(this);
            thread.start();
        }
    }

    public void stop() {
        running = false;
        if (thread != null) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Thread wurde unterbrochen.");
            }
        }
    }

    private void checkShooting(int milliSeconds){
        for(Weapon weapon: weapons){
            if(weapon.getLastShot() <= (weapon.getAttackSpeed() + (milliSeconds / 100))){
                Platform.runLater(() -> weapon.setLastShot(milliSeconds));
                Platform.runLater(() -> weapon.shoot());
                System.out.println("Shooting with: " + weapon.getName());
            }
        }
    }

}
