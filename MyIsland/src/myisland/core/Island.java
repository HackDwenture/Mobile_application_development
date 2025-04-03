package myisland.core;

import myisland.config.Settings;
import myisland.entities.Animal;
import myisland.entities.Plant;
import myisland.entities.Predator;
import myisland.entities.Herbivore;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Island {
    // ANSI color codes
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String CYAN = "\u001B[36m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";
    private static final String PURPLE = "\u001B[35m";
    private static final String WHITE = "\u001B[37m";
    private static final String BOLD = "\u001B[1m";

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

    public void initialize() {
        System.out.println(BLUE + BOLD + "╔════════════════════════════════════════╗");
        System.out.println("║" + YELLOW + "         ИНИЦИАЛИЗАЦИЯ ОСТРОВА     " + BLUE + "     ║");
        System.out.println("╚════════════════════════════════════════╝" + RESET);

        // Enhanced loading animation
        String[] frames = new String[] {
                "[          ]",
                "[█         ]",
                "[██        ]",
                "[███       ]",
                "[████      ]",
                "[█████     ]",
                "[██████    ]",
                "[███████   ]",
                "[████████  ]",
                "[█████████ ]",
                "[██████████]"
        };

        String[] colors = new String[] {RED, YELLOW, GREEN, CYAN, BLUE, PURPLE};
        String[] messages = new String[] {
                "Создание ландшафта...",
                "Посадка растений...",
                "Расселение травоядных...",
                "Размещение хищников...",
                "Настройка экосистемы...",
                "Запуск симуляции..."
        };

        for (int i = 0; i <= 100; i++) {
            int frameIndex = (i * frames.length) / 100;
            if (frameIndex >= frames.length) frameIndex = frames.length - 1;

            int colorIndex = (i * colors.length) / 100;
            if (colorIndex >= colors.length) colorIndex = colors.length - 1;

            int msgIndex = (i * messages.length) / 100;
            if (msgIndex >= messages.length) msgIndex = messages.length - 1;

            System.out.print("\r" + colors[colorIndex] + frames[frameIndex] + " " + messages[msgIndex] +
                    " (" + i + "%)" + RESET);

            try {
                Thread.sleep(30 + random.nextInt(40)); // Variable speed for more natural feel
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        System.out.println("\n" + GREEN + BOLD + "✓ Готово! Остров успешно инициализирован!" + RESET);

        // Initialize plants and animals
        for (Location[] row : locations) {
            for (Location location : row) {
                for (int i = 0; i < random.nextInt(5) + 3; i++) {
                    location.addPlant(Plant.createRandomPlant());
                }
                populateWithAnimals(location);
            }
        }

        scheduler.scheduleAtFixedRate(this::growPlants, 0, Settings.PLANT_GROWTH_INTERVAL, TimeUnit.SECONDS);
        scheduler.scheduleAtFixedRate(this::processAnimals, 0, Settings.ANIMAL_ACT_INTERVAL, TimeUnit.SECONDS);
        scheduler.scheduleAtFixedRate(this::printStatistics, 0, Settings.STATS_UPDATE_INTERVAL, TimeUnit.SECONDS);
    }

    private void populateWithAnimals(Location location) {
        for (int i = 0; i < random.nextInt(3); i++) {
            location.addAnimal(Herbivore.createRandomHerbivore());
        }
        if (random.nextDouble() < 0.3) {
            location.addAnimal(Predator.createRandomPredator());
        }
    }

    private void growPlants() {
        for (Location[] row : locations) {
            for (Location location : row) {
                if (random.nextDouble() < Settings.PLANT_GROWTH_PROBABILITY) {
                    location.growPlants();
                }
            }
        }
    }

    private void processAnimals() {
        for (Location[] row : locations) {
            for (Location location : row) {
                animalExecutor.submit(location::processAnimals);
            }
        }
    }

    private void printStatistics() {
        simulationStep++;

        // Собираем статистику
        Map<Class<?>, AtomicInteger> herbivoreStats = new TreeMap<>(Comparator.comparing(Class::getSimpleName));
        Map<Class<?>, AtomicInteger> predatorStats = new TreeMap<>(Comparator.comparing(Class::getSimpleName));
        Map<Class<?>, AtomicInteger> plantStats = new TreeMap<>(Comparator.comparing(Class::getSimpleName));

        int totalAnimals = 0;
        int totalPlants = 0;

        for (Location[] row : locations) {
            for (Location location : row) {
                totalPlants += location.getPlants().size();
                location.getPlants().forEach(plant ->
                        plantStats.computeIfAbsent(plant.getClass(), k -> new AtomicInteger()).incrementAndGet());

                totalAnimals += location.getAnimals().size();
                location.getAnimals().forEach(animal -> {
                    if (animal instanceof Predator) {
                        predatorStats.computeIfAbsent(animal.getClass(), k -> new AtomicInteger()).incrementAndGet();
                    } else {
                        herbivoreStats.computeIfAbsent(animal.getClass(), k -> new AtomicInteger()).incrementAndGet();
                    }
                });
            }
        }

        // Форматированный вывод с цветами
        System.out.println("\n" + BLUE + BOLD + "╔════════════════════════════════════════╗" + RESET);
        System.out.printf(BLUE + BOLD + "║ " + YELLOW + "%-38s " + BLUE + "║\n" + RESET, "=== Шаг " + simulationStep + " ===");
        System.out.println(BLUE + BOLD + "╠════════════════════════════════════════╣" + RESET);
        System.out.printf(BLUE + BOLD + "║ " + WHITE + "%-22s " + YELLOW + "%15d " + BLUE + "║\n" + RESET,
                "Всего животных:", totalAnimals);
        System.out.printf(BLUE + BOLD + "║ " + WHITE + "%-22s " + YELLOW + "%15d " + BLUE + "║\n" + RESET,
                "Всего растений:", totalPlants);
        System.out.println(BLUE + BOLD + "╠════════════════════════════════════════╣" + RESET);

        printCategory("ХИЩНИКИ", predatorStats, RED);
        printCategory("ТРАВОЯДНЫЕ", herbivoreStats, GREEN);
        printCategory("РАСТЕНИЯ", plantStats, CYAN);

        System.out.println(BLUE + BOLD + "╚════════════════════════════════════════╝" + RESET);
    }

    private void printCategory(String title, Map<Class<?>, AtomicInteger> stats, String color) {
        System.out.printf(BLUE + BOLD + "║ " + color + BOLD + "%-38s " + BLUE + "║\n" + RESET, ">>> " + title + " <<<");
        if (stats.isEmpty()) {
            System.out.printf(BLUE + BOLD + "║ " + WHITE + "%-38s " + BLUE + "║\n" + RESET, "  Нет данных");
        } else {
            stats.forEach((clazz, count) -> {
                String className = clazz.getSimpleName();
                String translatedName = TranslationService.translateEntity(className);
                System.out.printf(BLUE + BOLD + "║ " + color + "%-26s " + YELLOW + "%11d " + BLUE + "║\n" + RESET,
                        "  " + translatedName, count.get());
            });
        }

    }

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