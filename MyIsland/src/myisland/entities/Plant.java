package myisland.entities;

import myisland.plants.*;

import java.util.Random;

public abstract class Plant extends Entity {
    protected double weight;

    public static Plant createRandomPlant() {
        Random random = new Random();
        double roll = random.nextDouble();
        if (roll < 0.6) return new Grass();
        else if (roll < 0.9) return new BerryBush();
        else return new Tree();
    }

    @Override
    public void act() {
        weight += 0.01;
    }

    public double getWeight() {
        return weight;
    }
}
