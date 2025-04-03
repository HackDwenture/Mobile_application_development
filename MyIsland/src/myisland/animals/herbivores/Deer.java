package myisland.animals.herbivores;

import myisland.entities.Herbivore;

public class Deer extends Herbivore {
    public Deer() {
        weight = 300;
        maxPerCell = 20;
        maxSpeed = 4;
        foodRequired = 50;
        satiety = foodRequired * 0.7;
        canCrossRiver = true;
    }
}
