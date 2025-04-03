package myisland.animals.herbivores;

import myisland.entities.Herbivore;

public class Duck extends Herbivore {
    public Duck() {
        weight = 1;
        maxPerCell = 200;
        maxSpeed = 4;
        foodRequired = 0.15;
        satiety = foodRequired * 0.7;
        canCrossRiver = true;
    }
}
