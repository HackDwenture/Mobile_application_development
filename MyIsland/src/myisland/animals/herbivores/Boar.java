package myisland.animals.herbivores;

import myisland.entities.Herbivore;

public class Boar extends Herbivore {
    public Boar() {
        weight = 400;
        maxPerCell = 50;
        maxSpeed = 2;
        foodRequired = 50;
        satiety = foodRequired * 0.7;
        canCrossRiver = true;
    }
}
