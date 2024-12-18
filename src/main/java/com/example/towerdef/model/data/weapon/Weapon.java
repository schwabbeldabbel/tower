package com.example.towerdef.model.data.weapon;

import com.example.towerdef.model.data.weapon.fxmlelement.Bullet;
import com.example.towerdef.model.data.weapon.fxmlelement.BulletType;
import lombok.Getter;
import lombok.Setter;

@Getter
public class Weapon {

    private final WeaponName name;
    private int attackSpeed;
    private int damage;

    private final BulletType bulletType;

    private int powerBullets = -1;

    private int powerBulletsFired = 0;
    private int powerBulletDamage = 0;

    private final int powerDamageAddition;
    private final int powerAttackSpeedAddition;

    public Weapon(WeaponName name, int attackSpeed, int damage, BulletType bullet){
        this.name = name;
        this.attackSpeed = attackSpeed;
        this.damage = damage;
        this.bulletType = bullet;

        this.powerDamageAddition = (int) (damage * 0.2);
        this.powerAttackSpeedAddition = (int) (attackSpeed * 0.2);
    }

    public void resetData(){
        powerBulletDamage = 0;
        powerBulletDamage = 0;
    }

    public Bullet shoot(){
        if(powerBullets == 0){
            this.attackSpeed -= powerAttackSpeedAddition;
            this.damage -= powerDamageAddition;
            powerBullets--;
        }else if(powerBullets > -1){
            this.powerBulletsFired++;
            this.powerBulletDamage += powerDamageAddition;
            powerBullets--;
        }
        return new Bullet(bulletType);
    }

    public void overdrive(int powerBullets){
        if(this.powerBullets < 0){
            this.attackSpeed += this.powerAttackSpeedAddition;
            this.damage += this.powerDamageAddition;
            this.powerBullets = powerBullets;
        }
    }

}
