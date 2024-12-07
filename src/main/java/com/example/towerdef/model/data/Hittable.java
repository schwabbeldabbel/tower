package com.example.towerdef.model.data;

public interface Hittable {

    boolean hit(int damage);
    void heal();
    float getArmor();
    void die();

}
