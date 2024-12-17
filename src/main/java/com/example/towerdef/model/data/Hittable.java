package com.example.towerdef.model.data;

public interface Hittable {

    int getDamageBlocked();
    int getDamageTaken();
    int getDamageFired();
    int getLifeHealed();

    int hit(int damage);
    void heal();
    boolean isAlive();
    void die();

    String getStyleClass();

}
