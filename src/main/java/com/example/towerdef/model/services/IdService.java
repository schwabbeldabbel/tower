package com.example.towerdef.model.services;

public class IdService {

    public static int getPositionOfId(String id){
        String lastChar = id.substring(id.length()-1);
        if(lastChar.matches("\\d+")) return Integer.parseInt(lastChar);
        return -1;
    }

}
