package myisland.animals.predators;
import myisland.entities.Animal;
import myisland.entities.Predator;

public class Eagle extends Predator {
    public Eagle() {
        weight = 6;
        maxPerCell = 20;
        maxSpeed = 3;
        foodRequired = 1;
        satiety = foodRequired * 0.7;
        canCrossRiver = true;
    }

    @Override
    protected double getEatingProbability(Animal prey) {
        return switch (prey.getClass().getSimpleName()) {
            case "Rabbit", "Mouse" -> 0.9;
            case "Duck" -> 0.8;
            default -> 0.0;
        };
    }
}
