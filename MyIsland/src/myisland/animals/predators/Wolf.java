package myisland.animals.predators;
import myisland.entities.Animal;

import myisland.core.Location;
import myisland.entities.Predator;

public class Wolf extends Predator {
    public Wolf() {
        weight = 50;
        maxPerCell = 30;
        maxSpeed = 3;
        foodRequired = 8;
        satiety = foodRequired * 0.7;
        canCrossRiver = true;
    }

    // Стадное поведение
    @Override
    public void move() {
        int wolfCount = (int) location.getAnimals().stream()
                .filter(a -> a instanceof Wolf)
                .count();
        int steps = wolfCount > 1 ?
                location.getIsland().getRandom().nextInt(maxSpeed - 1) + 1 :
                location.getIsland().getRandom().nextInt(maxSpeed) + 1;

        for (int i = 0; i < steps; i++) {
            int dx = location.getIsland().getRandom().nextInt(3) - 1;
            int dy = location.getIsland().getRandom().nextInt(3) - 1;
            Location newLocation = location.getIsland().getRelativeLocation(location, dx, dy);
            if (newLocation != null && canMoveTo(newLocation) && newLocation.canAddAnimal(this.getClass())) {
                location.removeAnimal(this);
                newLocation.addAnimal(this);
                location = newLocation;
                break;
            }
        }
    }

    @Override
    protected double getEatingProbability(Animal prey) {
        return switch (prey.getClass().getSimpleName()) {
            case "Rabbit" -> 0.6;
            case "Mouse" -> 0.8;
            case "Goat" -> 0.6;
            case "Sheep" -> 0.7;
            case "Duck" -> 0.4;
            default -> 0.0;
        };
    }
}
