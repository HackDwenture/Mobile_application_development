package myisland.entities;

import myisland.core.Location;
import myisland.config.Settings;

import java.util.ArrayList;
import java.util.Random;

public abstract class Animal extends Entity {
    protected double weight;
    protected int maxPerCell;
    protected int maxSpeed;
    protected double foodRequired;
    protected double satiety;
    protected double satietyLossRate = 0.05;
    protected boolean canCrossRiver = false;

    public abstract boolean canEat(Animal animal);
    public abstract boolean canEat(Plant plant);

    @Override
    public void act() {
        eat();
        move();
        reproduce();
        loseSatiety();
    }

    public void eat() {
        if (this instanceof Predator) {
            for (Animal other : new ArrayList<>(location.getAnimals())) {
                if (other != this && canEat(other)) {
                    if (location.getIsland().getRandom().nextDouble() < getEatingProbability(other)) {
                        location.removeAnimal(other);
                        satiety = Math.min(satiety + other.getWeight(), foodRequired * 1.5);
                        return;
                    }
                }
            }
        }

        for (Plant plant : new ArrayList<>(location.getPlants())) {
            if (canEat(plant)) {
                location.removePlant(plant);
                satiety = Math.min(satiety + plant.getWeight(), foodRequired * 1.5);
                return;
            }
        }
    }

    protected double getEatingProbability(Animal prey) {
        return 0.0; // Переопределяется в конкретных классах
    }

    public void move() {
        if (maxSpeed == 0) return;
        int steps = location.getIsland().getRandom().nextInt(maxSpeed) + 1;
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

    protected boolean canMoveTo(Location newLocation) {
        return !newLocation.hasRiver() || canCrossRiver;
    }

    public void reproduce() {
        if (satiety < foodRequired * 0.7) return;
        if (location.getIsland().getRandom().nextDouble() > Settings.REPRODUCTION_PROBABILITY) return;

        for (Animal other : location.getAnimals()) {
            if (other != this && other.getClass() == this.getClass()) {
                try {
                    Animal offspring = this.getClass().getDeclaredConstructor().newInstance();
                    offspring.satiety = satiety * 0.3;
                    location.addAnimal(offspring);
                    satiety *= 0.7;
                    return;
                } catch (ReflectiveOperationException e) {
                    System.err.println("Ошибка при создании потомка: " + e.getMessage());
                }
            }
        }
    }

    public void loseSatiety() {
        satiety -= satietyLossRate;
        if (satiety < 0) satiety = 0;
    }

    public double getWeight() {
        return weight;
    }

    public int getMaxPerCell() {
        return maxPerCell;
    }

    public double getSatiety() {
        return satiety;
    }
}
