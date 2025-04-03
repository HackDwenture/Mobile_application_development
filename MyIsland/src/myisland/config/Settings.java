package myisland.config;

public class Settings {
    // Размеры острова
    public static final int ISLAND_WIDTH = 30;
    public static final int ISLAND_HEIGHT = 15;

    // Интервалы обновления (в секундах)
    public static final int PLANT_GROWTH_INTERVAL = 3;   // Частота роста растений
    public static final int ANIMAL_ACT_INTERVAL = 2;     // Частота действий животных
    public static final int STATS_UPDATE_INTERVAL = 5;   // Частота вывода статистики

    // Ограничения и вероятности
    public static final int MAX_PLANTS_PER_CELL = 150;        // Макс. растений на клетку
    public static final double PLANT_GROWTH_PROBABILITY = 0.3; // Шанс роста нового растения
    public static final double REPRODUCTION_PROBABILITY = 0.25; // Шанс размножения животного
}
