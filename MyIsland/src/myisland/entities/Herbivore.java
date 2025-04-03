package myisland.entities;

import myisland.animals.herbivores.*;

import java.util.Random;

public abstract class Herbivore extends Animal {

    public static Herbivore createRandomHerbivore() {
        Random random = new Random();
        double roll = random.nextDouble();
        if (roll < 0.15) return new Horse();
        else if (roll < 0.3) return new Deer();
        else if (roll < 0.5) return new Rabbit();
        else if (roll < 0.7) return new Mouse();
        else if (roll < 0.8) return new Goat();
        else if (roll < 0.9) return new Sheep();
        else if (roll < 0.95) return new Boar();
        else if (roll < 0.97) return new Buffalo();
        else if (roll < 0.99) return new Duck();
        else return new Caterpillar();
    }

    @Override
    public boolean canEat(Animal animal) {
        return (this instanceof Duck && animal instanceof Caterpillar);
    }

    @Override
    public boolean canEat(Plant plant) {
        return true;
    }
}
