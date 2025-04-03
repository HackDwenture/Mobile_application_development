package myisland.animals.herbivores;

import myisland.entities.Herbivore;

public class Rabbit extends Herbivore {
    public Rabbit() {
        weight = 2;
        maxPerCell = 150;
        maxSpeed = 2;
        foodRequired = 0.45;
        satiety = foodRequired * 0.7;
        canCrossRiver = false;
    }
}
