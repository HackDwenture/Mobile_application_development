package myisland;

import myisland.core.Island;

public class MyIsland {
    public static void main(String[] args) {
        System.out.println("Работу выполнил студент группы 3ИСИП522, Яцук Кирилл.");
        Island island = new Island(30, 15);
        island.initialize();
    }
}
