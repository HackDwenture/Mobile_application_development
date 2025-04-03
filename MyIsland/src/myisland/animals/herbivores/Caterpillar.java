package myisland.animals.herbivores;

import myisland.entities.Herbivore;

public class Caterpillar extends Herbivore {
    public Caterpillar() {
        weight = 0.01;
        maxPerCell = 1000;
        maxSpeed = 0;
        foodRequired = 0.001;
        satiety = 0;
        canCrossRiver = false;
    }
}
