package com.example.towerdef.model.gamelogic.runtime;

import com.example.towerdef.model.data.human.HumanUnit;
import com.example.towerdef.model.data.tower.Tower;
import javafx.geometry.Bounds;
import javafx.scene.Node;

import java.util.List;

public class Validator {

    public boolean isColliding(Node bullet, Node target) {
        Bounds bulletBounds = bullet.getBoundsInParent();
        Bounds targetBounds = target.getBoundsInParent();
        return  bulletBounds.intersects(targetBounds);
    }

    public String isWinning(List<HumanUnit> humans, Tower tower){
        humans.removeIf(humanUnit -> !humanUnit.isAlive());
        if(humans.isEmpty()){
            return "Der Turm hat gewonnen!";
        }
        if(!tower.isAlive()){
            return "Die Menschen haben gewonnen!";
        }
        return null;
    }

    public String getInValueInLimits(String value, int min, int max){
        if(value.matches("\\d+")){
            if (value.length() > String.valueOf(max).length()) return String.valueOf(max);
            long intValue = Long.parseLong(value);
            if(intValue < min){
                return String.valueOf(min);
            }else if(intValue > max){
                return String.valueOf(max);
            }
            return value;
        }
        return "0";
    }

}
