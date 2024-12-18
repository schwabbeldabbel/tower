package com.example.towerdef.model.data;

public interface Hittable {

    int hit(int damage);
    int heal();
    boolean isAlive();
    void die();
    int getHealth();
    int getMaxHealth();

    String getStyleClass();

}
