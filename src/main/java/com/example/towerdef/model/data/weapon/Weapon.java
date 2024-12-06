package com.example.towerdef.model.data.weapon;

import com.example.towerdef.controller.GameViewController;
import com.example.towerdef.model.data.weapon.fxmlelement.Bullet;
import com.example.towerdef.model.data.weapon.fxmlelement.BulletType;
import lombok.Getter;
import lombok.Setter;

@Getter
public class Weapon {

    private final String name;
    private final int attackSpeed;
    private final int damage;
    @Getter@Setter
    private int lastShot;

    private final BulletType bulletType;
    @Setter
    private int xPos;
    @Setter
    private int yPos;

    private GameViewController gameViewController;

    public Weapon(String name, int attackSpeed, int damage, BulletType bullet){
        this.name = name;
        this.attackSpeed = attackSpeed;
        this.damage = damage;
        this.bulletType = bullet;
    }

    public void addObserver(GameViewController gameViewController){
        this.gameViewController = gameViewController;
    }

    //TODO bullet type als einzige Variable
    public void shoot(){
        Bullet bullet = new Bullet(xPos, yPos, bulletType, gameSpeed, bulletType.getTravelTime());
        gameViewController.addBullet(bullet);
    }

}
