package myisland.core;

import myisland.config.Settings;
import myisland.entities.Animal;
import myisland.entities.Plant;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Location {
    private final int x;
    private final int y;
    private final Island island;
    private final boolean hasRiver;

    private final List<Animal> animals = new CopyOnWriteArrayList<>();
    private final List<Plant> plants = new CopyOnWriteArrayList<>();

    public Location(int x, int y, Island island, boolean hasRiver) {
        this.x = x;
        this.y = y;
        this.island = island;
        this.hasRiver = hasRiver;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Island getIsland() {
        return island;
    }

    public boolean hasRiver() {
        return hasRiver;
    }

    public List<Animal> getAnimals() {
        return Collections.unmodifiableList(animals);
    }

    public List<Plant> getPlants() {
        return Collections.unmodifiableList(plants);
    }

    public void processAnimals() {
        for (Animal animal : new CopyOnWriteArrayList<>(animals)) {
            animal.act();
            if (animal.getSatiety() <= 0) {
                animals.remove(animal);
            }
        }
    }

    public void growPlants() {
        if (plants.size() < Settings.MAX_PLANTS_PER_CELL) {
            plants.add(Plant.createRandomPlant());
        }
    }

    public void addAnimal(Animal animal) {
        if (canAddAnimal(animal.getClass())) {
            animal.setLocation(this);
            animals.add(animal);
        }
    }

    public void removeAnimal(Animal animal) {
        animals.remove(animal);
    }

    public void addPlant(Plant plant) {
        if (plants.size() < Settings.MAX_PLANTS_PER_CELL) {
            plants.add(plant);
        }
    }

    public void removePlant(Plant plant) {
        plants.remove(plant);
    }

    public boolean canAddAnimal(Class<? extends Animal> animalClass) {
        long count = animals.stream()
                .filter(a -> a.getClass().equals(animalClass))
                .count();

        try {
            Animal temp = animalClass.getDeclaredConstructor().newInstance();
            return count < temp.getMaxPerCell();
        } catch (ReflectiveOperationException e) {
            return false;
        }
    }
}
