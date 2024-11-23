package com.example.towerdef.model.gamelogic.setup;

import com.example.towerdef.model.data.tower.TowerNames;
import com.example.towerdef.model.data.weapon.WeaponName;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TowerNameService {

    public static String getRandomTowerName(){
        List<String> towerNameList = new ArrayList<>();
        for(TowerNames name: TowerNames.values()){
            towerNameList.add(name.getFullName());
        }
        Random rand = new Random();
        return towerNameList.get(rand.nextInt(towerNameList.size()));

    }

}
