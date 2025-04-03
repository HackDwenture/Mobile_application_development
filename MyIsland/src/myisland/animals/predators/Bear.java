package myisland.animals.predators;
import myisland.entities.Animal;

import myisland.entities.Predator;

public class Bear extends Predator {
    public Bear() {
        weight = 500;
        maxPerCell = 5;
        maxSpeed = 2;
        foodRequired = 80;
        satiety = foodRequired * 0.7;
        canCrossRiver = true;
    }

    @Override
    protected double getEatingProbability(Animal prey) {
        return switch (prey.getClass().getSimpleName()) {
            case "Wolf" -> 0.8;
            case "Rabbit" -> 0.8;
            case "Mouse" -> 0.9;
            case "Goat" -> 0.7;
            case "Sheep" -> 0.7;
            case "Boar" -> 0.5;
            default -> 0.0;
        };
    }
}
