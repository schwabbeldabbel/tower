package com.example.towerdef.model.data.weapon;

import com.example.towerdef.model.data.weapon.fxmlelement.Bullet;
import com.example.towerdef.model.data.weapon.fxmlelement.BulletType;
import lombok.Getter;

@Getter
public class Weapon {

    private final WeaponName name;
    private final int attackSpeed;
    private final int damage;

    private final BulletType bulletType;

    public Weapon(WeaponName name, int attackSpeed, int damage, BulletType bullet){
        this.name = name;
        this.attackSpeed = attackSpeed;
        this.damage = damage;
        this.bulletType = bullet;
    }

    public Bullet shoot(){
        return new Bullet(bulletType);
    }

}
