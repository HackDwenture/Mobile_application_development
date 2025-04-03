package myisland.animals.herbivores;

import myisland.entities.Herbivore;

public class Goat extends Herbivore {
    public Goat() {
        weight = 60;
        maxPerCell = 140;
        maxSpeed = 3;
        foodRequired = 10;
        satiety = foodRequired * 0.7;
        canCrossRiver = true;
    }
}
