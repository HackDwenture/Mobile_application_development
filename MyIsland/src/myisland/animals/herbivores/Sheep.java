package myisland.animals.herbivores;

import myisland.entities.Herbivore;

public class Sheep extends Herbivore {
    public Sheep() {
        weight = 70;
        maxPerCell = 140;
        maxSpeed = 3;
        foodRequired = 15;
        satiety = foodRequired * 0.7;
        canCrossRiver = false;
    }
}
