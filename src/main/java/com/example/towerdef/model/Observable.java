package com.example.towerdef.model;

public interface Observable {
    
    void addObserver(Observer observer);
    
    void removeObserver(Observer observer);
    
}
