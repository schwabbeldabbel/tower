package com.example.towerdef.model.data;

public interface Hittable {

    int hit(int damage);
    void heal();
    boolean isAlive();
    void die();

}
