import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

//Главный класс острова
public class GreenIsland {
    public static void main(String[] args) {
        System.out.println("=== СИМУЛЯЦИЯ ЭКОСИСТЕМЫ ОСТРОВА ===");
        Island island = new Island(30, 15);
        island.initialize();
    }
}
//--

//Класс с настройками 
class Settings {
    // Размеры острова
    public static final int ISLAND_WIDTH = 30;  
    public static final int ISLAND_HEIGHT = 15;  
    // Интервалы обновления (в секундах)
    public static final int PLANT_GROWTH_INTERVAL = 3;  
    public static final int ANIMAL_ACT_INTERVAL = 2;    
    public static final int STATS_UPDATE_INTERVAL = 5;  
    // Лимиты и вероятности
    public static final int MAX_PLANTS_PER_CELL = 150;  
    public static final double PLANT_GROWTH_PROBABILITY = 0.3;  
    public static final double REPRODUCTION_PROBABILITY = 0.25; 
}
//--

//Класс представляющий остров с экосистемой
class Island {
    private final Location[][] locations;         
    private final ScheduledExecutorService scheduler;  
    private final ExecutorService animalExecutor;      
    private final Random random = new Random();        
    private int simulationStep = 0;                 
    public Island(int width, int height) {
        this.locations = new Location[width][height];
        this.scheduler = Executors.newScheduledThreadPool(3);
        this.animalExecutor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        initializeTerrain();
    }
//--

//Инициализация местности
    private void initializeTerrain() {
        int riverX = locations.length / 2;
        for (int y = 0; y < locations[0].length; y++) {
            for (int x = riverX - 1; x <= riverX + 1; x++) {
                if (x >= 0 && x < locations.length) {
                    locations[x][y] = new Location(x, y, this, true);
                }
            }
        }

        for (int x = 0; x < locations.length; x++) {
            for (int y = 0; y < locations[x].length; y++) {
                if (locations[x][y] == null) {
                    locations[x][y] = new Location(x, y, this, false);
                }
            }
        }
    }
//--

//Инициализация острова - заселение растениями и животными
    public void initialize() {
        System.out.println("Инициализация острова...");
        for (Location[] row : locations) {
            for (Location location : row) {
                for (int i = 0; i < random.nextInt(5) + 3; i++) {
                    location.addPlant(Plant.createRandomPlant());
                }
                populateWithAnimals(location);
            }
        }

        //Периодические задачи:
        scheduler.scheduleAtFixedRate(this::growPlants, 0, Settings.PLANT_GROWTH_INTERVAL, TimeUnit.SECONDS);
        scheduler.scheduleAtFixedRate(this::processAnimals, 0, Settings.ANIMAL_ACT_INTERVAL, TimeUnit.SECONDS);
        scheduler.scheduleAtFixedRate(this::printStatistics, 0, Settings.STATS_UPDATE_INTERVAL, TimeUnit.SECONDS);
    }

    //Заселение локации животными
    private void populateWithAnimals(Location location) {
        for (int i = 0; i < random.nextInt(3); i++) {
            location.addAnimal(Herbivore.createRandomHerbivore());
        }   
        if (random.nextDouble() < 0.3) {
            location.addAnimal(Predator.createRandomPredator());
        }
    }

 
    //Рост растений 
    private void growPlants() {
        for (Location[] row : locations) {
            for (Location location : row) {
                if (random.nextDouble() < Settings.PLANT_GROWTH_PROBABILITY) {
                    location.growPlants();
                }
            }
        }
    }


    //Обработка действий всех животных 
    private void processAnimals() {
        for (Location[] row : locations) {
            for (Location location : row) {
                animalExecutor.submit(location::processAnimals);
            }
        }
    }

    //Вывод статистики по острову
    private void printStatistics() {
        simulationStep++;
        Map<Class<?>, AtomicInteger> herbivoreStats = new TreeMap<>(Comparator.comparing(Class::getSimpleName)); // Травоядные
        Map<Class<?>, AtomicInteger> predatorStats = new TreeMap<>(Comparator.comparing(Class::getSimpleName));  // Хищники
        Map<Class<?>, AtomicInteger> plantStats = new TreeMap<>(Comparator.comparing(Class::getSimpleName));     // Растения
        int totalAnimals = 0; 
        int totalPlants = 0; 

        // Собираем статистику по всем локациям
        for (Location[] row : locations) {
            for (Location location : row) {
                // Получаем список растений
                List<Plant> plants = location.getPlants();
                totalPlants += plants.size();
                for (Plant plant : plants) {
                    plantStats.computeIfAbsent(plant.getClass(), k -> new AtomicInteger()).incrementAndGet();
                }
                // Получаем список животных
                List<Animal> animals = location.getAnimals();
                totalAnimals += animals.size();
                for (Animal animal : animals) {
                    if (animal instanceof Predator) {
                        predatorStats.computeIfAbsent(animal.getClass(), k -> new AtomicInteger()).incrementAndGet();
                    } else {
                        herbivoreStats.computeIfAbsent(animal.getClass(), k -> new AtomicInteger()).incrementAndGet();
                    }
                }
            }
        }

        // Выводим статистику
        System.out.println("\n=== Шаг " + simulationStep + " ===");
        System.out.println("Всего животных: " + totalAnimals);
        System.out.println("Всего растений: " + totalPlants);
        System.out.println("\n>>> ХИЩНИКИ <<<");
        predatorStats.forEach((clazz, count) -> 
            System.out.printf("  %-12s: %d\n", Entity.getRussianName(clazz), count.get()));

        System.out.println("\n>>> ТРАВОЯДНЫЕ <<<");
        herbivoreStats.forEach((clazz, count) -> 
            System.out.printf("  %-12s: %d\n", Entity.getRussianName(clazz), count.get()));
        
        System.out.println("\n>>> РАСТЕНИЯ <<<");
        plantStats.forEach((clazz, count) -> 
            System.out.printf("  %-12s: %d\n", Entity.getRussianName(clazz), count.get()));
        
        System.out.println("=".repeat(40));
    }


    //Получение соседней локации относительно текущей
    public Location getRelativeLocation(Location current, int dx, int dy) {
        int newX = current.getX() + dx;
        int newY = current.getY() + dy;
        if (newX >= 0 && newX < locations.length && newY >= 0 && newY < locations[0].length) {
            return locations[newX][newY];
        }
        return null; 
    }

    public Random getRandom() {
        return random;
    }
}

//Локация
class Location {
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


    //Обработка животных в локации
    public void processAnimals() {
        for (Animal animal : animals) {
            animal.act();
            if (animal.getSatiety() <= 0) {
                animals.remove(animal);
            }
        }
    }


    //Рост растений в локации
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

    public void addPlant(Plant plant) {
        if (plants.size() < Settings.MAX_PLANTS_PER_CELL) {
            plants.add(plant);
        }
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
    public void removeAnimal(Animal animal) {
        animals.remove(animal);
    }

    public void removePlant(Plant plant) {
        plants.remove(plant);
    }
}

//Все сущности
abstract class Entity {
    protected Location location; 
    public abstract void act();
    public void setLocation(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }
    
    public static String getRussianName(Class<?> clazz) {
        if (clazz == Wolf.class) return "Волк";
        if (clazz == Boa.class) return "Удав";
        if (clazz == Fox.class) return "Лиса";
        if (clazz == Bear.class) return "Медведь";
        if (clazz == Eagle.class) return "Орел";
        if (clazz == Horse.class) return "Лошадь";
        if (clazz == Deer.class) return "Олень";
        if (clazz == Rabbit.class) return "Кролик";
        if (clazz == Mouse.class) return "Мышь";
        if (clazz == Goat.class) return "Коза";
        if (clazz == Sheep.class) return "Овца";
        if (clazz == Boar.class) return "Кабан";
        if (clazz == Buffalo.class) return "Буйвол";
        if (clazz == Duck.class) return "Утка";
        if (clazz == Caterpillar.class) return "Гусеница";
        if (clazz == Grass.class) return "Трава";
        if (clazz == BerryBush.class) return "Ягодный куст";
        if (clazz == Tree.class) return "Дерево";
        return clazz.getSimpleName(); 
    }
}

//Все животные
abstract class Animal extends Entity {
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

    //Метод для питания животного
    public void eat() {
        if (this instanceof Predator) {
            for (Animal animal : new ArrayList<>(location.getAnimals())) {
                if (animal != this && canEat(animal)) {
                    if (location.getIsland().getRandom().nextDouble() < getEatingProbability(animal)) {
                        location.removeAnimal(animal);
                        satiety = Math.min(satiety + animal.getWeight(), foodRequired * 1.5);
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
        if (this instanceof Wolf) {
            if (prey instanceof Rabbit) return 0.6;
            if (prey instanceof Mouse) return 0.8;
            if (prey instanceof Goat) return 0.6;
            if (prey instanceof Sheep) return 0.7;
            if (prey instanceof Duck) return 0.4;
        }
        if (this instanceof Fox) {
            if (prey instanceof Rabbit) return 0.7;
            if (prey instanceof Mouse) return 0.9;
            if (prey instanceof Duck) return 0.6;
            if (prey instanceof Caterpillar) return 0.4;
        }
        if (this instanceof Bear) {
            if (prey instanceof Wolf) return 0.8;
            if (prey instanceof Rabbit) return 0.8;
            if (prey instanceof Mouse) return 0.9;
            if (prey instanceof Goat) return 0.7;
            if (prey instanceof Sheep) return 0.7;
            if (prey instanceof Boar) return 0.5;
        }
        if (this instanceof Eagle) {
            if (prey instanceof Rabbit) return 0.9;
            if (prey instanceof Mouse) return 0.9;
            if (prey instanceof Duck) return 0.8;
        }
        if (this instanceof Boa) {
            if (prey instanceof Rabbit) return 0.2;
            if (prey instanceof Mouse) return 0.4;
            if (prey instanceof Duck) return 0.1;
        }
        if (this instanceof Duck && prey instanceof Caterpillar) {
            return 0.9;
        }
        return 0.0; 
    }

    //Перемещение животного
    public void move() {
        if (maxSpeed == 0) return;
        int steps = location.getIsland().getRandom().nextInt(maxSpeed) + 1;
        for (int i = 0; i < steps; i++) {
            int dx = location.getIsland().getRandom().nextInt(3) - 1;
            int dy = location.getIsland().getRandom().nextInt(3) - 1;
            
            // Получаем новую локацию
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

        for (Animal animal : location.getAnimals()) {
            if (animal != this && animal.getClass() == this.getClass()) {
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

//Травоядные животные
abstract class Herbivore extends Animal {
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

//Хищные животные
abstract class Predator extends Animal {
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

//Растения
abstract class Plant extends Entity {
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

//Единицы пищи
class Grass extends Plant {
    public Grass() {
        this.weight = 0.5; 
    }
}

class BerryBush extends Plant {
    public BerryBush() {
        this.weight = 1.5; 
    }
}

class Tree extends Plant {
    public Tree() {
        this.weight = 3.0; 
    }
}

class Horse extends Herbivore {
    public Horse() {
        weight = 400;
        maxPerCell = 20;
        maxSpeed = 4;
        foodRequired = 60;
        satiety = foodRequired * 0.7;
        canCrossRiver = false;
    }
}

class Deer extends Herbivore {
    public Deer() {
        weight = 300;
        maxPerCell = 20;
        maxSpeed = 4;
        foodRequired = 50;
        satiety = foodRequired * 0.7;
        canCrossRiver = true;
    }
}

class Rabbit extends Herbivore {
    public Rabbit() {
        weight = 2;
        maxPerCell = 150;
        maxSpeed = 2;
        foodRequired = 0.45;
        satiety = foodRequired * 0.7;
        canCrossRiver = false;
    }
}

class Mouse extends Herbivore {
    public Mouse() {
        weight = 0.05;
        maxPerCell = 500;
        maxSpeed = 1;
        foodRequired = 0.01;
        satiety = foodRequired * 0.7;
        canCrossRiver = false;
    }
}

class Goat extends Herbivore {
    public Goat() {
        weight = 60;
        maxPerCell = 140;
        maxSpeed = 3;
        foodRequired = 10;
        satiety = foodRequired * 0.7;
        canCrossRiver = true;
    }
}

class Sheep extends Herbivore {
    public Sheep() {
        weight = 70;
        maxPerCell = 140;
        maxSpeed = 3;
        foodRequired = 15;
        satiety = foodRequired * 0.7;
        canCrossRiver = false;
    }
}

class Boar extends Herbivore {
    public Boar() {
        weight = 400;
        maxPerCell = 50;
        maxSpeed = 2;
        foodRequired = 50;
        satiety = foodRequired * 0.7;
        canCrossRiver = true;
    }
}

class Buffalo extends Herbivore {
    public Buffalo() {
        weight = 700;
        maxPerCell = 10;
        maxSpeed = 3;
        foodRequired = 100;
        satiety = foodRequired * 0.7;
        canCrossRiver = true;
    }
}

class Duck extends Herbivore {
    public Duck() {
        weight = 1;
        maxPerCell = 200;
        maxSpeed = 4;
        foodRequired = 0.15;
        satiety = foodRequired * 0.7;
        canCrossRiver = true;
    }
}

class Caterpillar extends Herbivore {
    public Caterpillar() {
        weight = 0.01;
        maxPerCell = 1000;
        maxSpeed = 0;
        foodRequired = 0.001;
        satiety = 0;
        canCrossRiver = false;
    }
}

class Wolf extends Predator {
    public Wolf() {
        weight = 50;
        maxPerCell = 30;
        maxSpeed = 3;
        foodRequired = 8;
        satiety = foodRequired * 0.7;
        canCrossRiver = true;
    }
    
    //Переопределенный метод движения для стайного поведения
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
}

class Boa extends Predator {
    public Boa() {
        weight = 15;
        maxPerCell = 30;
        maxSpeed = 1;
        foodRequired = 3;
        satiety = foodRequired * 0.7;
        canCrossRiver = false;
    }
}

class Fox extends Predator {
    public Fox() {
        weight = 8;
        maxPerCell = 30;
        maxSpeed = 2;
        foodRequired = 2;
        satiety = foodRequired * 0.7;
        canCrossRiver = false;
    }
}

class Bear extends Predator {
    public Bear() {
        weight = 500;
        maxPerCell = 5;
        maxSpeed = 2;
        foodRequired = 80;
        satiety = foodRequired * 0.7;
        canCrossRiver = true;
    }
}

class Eagle extends Predator {
    public Eagle() {
        weight = 6;
        maxPerCell = 20;
        maxSpeed = 3;
        foodRequired = 1;
        satiety = foodRequired * 0.7;
        canCrossRiver = true;
    }
}