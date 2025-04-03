package myisland.animals.predators;
import myisland.entities.Animal;

import myisland.entities.Predator;

public class Boa extends Predator {
    public Boa() {
        weight = 15;
        maxPerCell = 30;
        maxSpeed = 1;
        foodRequired = 3;
        satiety = foodRequired * 0.7;
        canCrossRiver = false;
    }

    @Override
    protected double getEatingProbability(Animal prey) {
        return switch (prey.getClass().getSimpleName()) {
            case "Rabbit" -> 0.2;
            case "Mouse" -> 0.4;
            case "Duck" -> 0.1;
            default -> 0.0;
        };
    }
}
