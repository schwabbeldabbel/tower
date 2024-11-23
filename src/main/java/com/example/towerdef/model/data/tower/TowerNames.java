package com.example.towerdef.model.data.tower;

public enum TowerNames {

    DONNERNDER_TOTENSCHLAEGER("Donnernder Totenschläger"),
    FLAMMENDE_FURIE("Flammende Furie"),
    SCHATTENZERMALMER("Schattenzermalmer"),
    BLITZENDE_VERDERBNIS("Blitzende Verderbnis"),
    HOELLISCHE_HARPUNE("Höllische Harpune"),
    FROSTIGER_PEINIGER("Frostiger Peiniger"),
    FEUERSCHWINGENDER_FOLTERMEISTER("Feuerschwingender Foltermeister"),
    STURMHEULER("Sturmheuler"),
    MORGENROETE_DES_GRAUENS("Morgenröte des Grauens"),
    FLEISCHREISSER("Fleischreißer");

    private final String fullName;

    TowerNames(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }
}
