package myisland.core;

import java.util.Map;
import java.util.HashMap;

public class TranslationService {
    private static final Map<String, String> ANIMAL_TRANSLATIONS = new HashMap<>();
    private static final Map<String, String> PLANT_TRANSLATIONS = new HashMap<>();

    static {
        // Инициализация переводов для животных
        ANIMAL_TRANSLATIONS.put("Bear", "Медведь");
        ANIMAL_TRANSLATIONS.put("Boa", "Удав");
        ANIMAL_TRANSLATIONS.put("Eagle", "Орел");
        ANIMAL_TRANSLATIONS.put("Fox", "Лиса");
        ANIMAL_TRANSLATIONS.put("Wolf", "Волк");
        ANIMAL_TRANSLATIONS.put("Boar", "Кабан");
        ANIMAL_TRANSLATIONS.put("Buffalo", "Буйвол");
        ANIMAL_TRANSLATIONS.put("Deer", "Олень");
        ANIMAL_TRANSLATIONS.put("Duck", "Утка");
        ANIMAL_TRANSLATIONS.put("Goat", "Коза");
        ANIMAL_TRANSLATIONS.put("Horse", "Лошадь");
        ANIMAL_TRANSLATIONS.put("Mouse", "Мышь");
        ANIMAL_TRANSLATIONS.put("Rabbit", "Кролик");
        ANIMAL_TRANSLATIONS.put("Sheep", "Овца");

        // Инициализация переводов для растений
        PLANT_TRANSLATIONS.put("BerryBush", "Ягодный куст");
        PLANT_TRANSLATIONS.put("Grass", "Трава");
        PLANT_TRANSLATIONS.put("Tree", "Дерево");
    }

    public static String translateAnimal(String englishName) {
        return ANIMAL_TRANSLATIONS.getOrDefault(englishName, englishName);
    }

    public static String translatePlant(String englishName) {
        return PLANT_TRANSLATIONS.getOrDefault(englishName, englishName);
    }

    public static String translateEntity(String englishName) {
        String translation = ANIMAL_TRANSLATIONS.get(englishName);
        if (translation == null) {
            translation = PLANT_TRANSLATIONS.get(englishName);
        }
        return translation != null ? translation : englishName;
    }
}