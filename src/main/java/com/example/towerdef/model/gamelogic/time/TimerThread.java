package com.example.towerdef.model.gamelogic.time;

import com.example.towerdef.controller.GameViewController;
import com.example.towerdef.model.data.weapon.Weapon;
import javafx.application.Platform;
import javafx.scene.control.Label;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class TimerThread implements Runnable {
    @Getter
    private int time;
    private volatile boolean running;
    private Thread thread;

    private GameViewController gameViewController;

    @Setter
    private int speed;

    public TimerThread(Speed speed, GameViewController gameViewController) {
        this.speed = speed.getMiliseconds();
        this.time = 0;
        this.running = false;
        this.gameViewController = gameViewController;
        this.run();
    }

    @Override
    public void run() {
        while (running) {
            try {
                Thread.sleep(speed);
                time++;
                Platform.runLater(() -> gameViewController.notify(time));
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


}
