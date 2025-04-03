package myisland.animals.predators;
import myisland.entities.Animal;

import myisland.entities.Predator;

public class Fox extends Predator {
    public Fox() {
        weight = 8;
        maxPerCell = 30;
        maxSpeed = 2;
        foodRequired = 2;
        satiety = foodRequired * 0.7;
        canCrossRiver = false;
    }

    @Override
    protected double getEatingProbability(Animal prey) {
        return switch (prey.getClass().getSimpleName()) {
            case "Rabbit" -> 0.7;
            case "Mouse" -> 0.9;
            case "Duck" -> 0.6;
            case "Caterpillar" -> 0.4;
            default -> 0.0;
        };
    }
}
