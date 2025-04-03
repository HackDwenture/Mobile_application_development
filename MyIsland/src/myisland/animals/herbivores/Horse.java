package myisland.animals.herbivores;

import myisland.entities.Herbivore;

public class Horse extends Herbivore {
    public Horse() {
        weight = 400;
        maxPerCell = 20;
        maxSpeed = 4;
        foodRequired = 60;
        satiety = foodRequired * 0.7;
        canCrossRiver = false;
    }
}
