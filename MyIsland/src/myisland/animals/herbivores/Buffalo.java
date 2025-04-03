package myisland.animals.herbivores;

import myisland.entities.Herbivore;

public class Buffalo extends Herbivore {
    public Buffalo() {
        weight = 700;
        maxPerCell = 10;
        maxSpeed = 3;
        foodRequired = 100;
        satiety = foodRequired * 0.7;
        canCrossRiver = true;
    }
}
