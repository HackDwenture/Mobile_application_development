package myisland.animals.herbivores;

import myisland.entities.Herbivore;

public class Mouse extends Herbivore {
    public Mouse() {
        weight = 0.05;
        maxPerCell = 500;
        maxSpeed = 1;
        foodRequired = 0.01;
        satiety = foodRequired * 0.7;
        canCrossRiver = false;
    }
}
