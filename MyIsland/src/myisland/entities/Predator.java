package myisland.entities;

import myisland.animals.predators.*;

import java.util.Random;

public abstract class Predator extends Animal {

    public static Predator createRandomPredator() {
        Random random = new Random();
        double roll = random.nextDouble();
        if (roll < 0.2) return new Wolf();
        else if (roll < 0.4) return new Boa();
        else if (roll < 0.7) return new Fox();
        else if (roll < 0.85) return new Bear();
        else return new Eagle();
    }

    @Override
    public boolean canEat(Plant plant) {
        return false;
    }

    @Override
    public boolean canEat(Animal animal) {
        return !(animal instanceof Predator);
    }
}
