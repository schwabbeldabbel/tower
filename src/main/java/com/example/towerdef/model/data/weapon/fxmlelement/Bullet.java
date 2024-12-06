package com.example.towerdef.model.data.weapon.fxmlelement;

import com.example.towerdef.model.gamelogic.time.Speed;
import javafx.scene.shape.Rectangle;

public class Bullet extends Rectangle implements Runnable{

    private BulletType type;

    private boolean alive;
    private Speed speed;
    private int travelSpeed;

    private volatile boolean running;
    private Thread thread;

    public Bullet(int x, int y, BulletType type, Speed speed, int travelSpeed) {
        super(type.getBulletWidth(), type.getBulletWidth(), type.getColor());
        this.type = type;
        setTranslateX(x);
        setTranslateY(y);
        this.alive = true;
        this.speed = speed;
        this.travelSpeed = travelSpeed;
    }

    private void travel(){
        setTranslateX(getTranslateX() + 10);
    }

    public void hit(){
        this.alive = false;
    }

    public void start() {
        if (!running) {
            running = true;
            thread = new Thread(this);
            thread.start();
        }
    }

    @Override
    public void run() {
        while (running) {
            try {
                Thread.sleep(speed.getMiliseconds() + travelSpeed);
                travel();

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Thread wurde unterbrochen.");
            }
        }
    }
}
