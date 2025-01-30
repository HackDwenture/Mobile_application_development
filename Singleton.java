import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

enum OrderStatus {
    NEW, IN_PROGRESS, DELIVERED, CANCELLED
}

enum Season {
    WINTER, SPRING, SUMMER, AUTUMN
}

class DatabaseConnection {
    private static DatabaseConnection instance;

    private DatabaseConnection() {
        System.out.println("Подключение к базе данных создано.");
    }

    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public void connect() {
        System.out.println("Вы подключены к базе данных.");
    }
}

class Logger {
    private static Logger instance;
    private List<String> logs = new ArrayList<>();

    private Logger() {}

    public static synchronized Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }

    public void log(String message) {
        logs.add(message);
        System.out.println("Сообщение добавлено в лог: " + message);
    }

    public void displayLogs() {
        System.out.println("Все сообщения логов:");
        for (String log : logs) {
            System.out.println(log);
        }
    }
}

class Order {
    private String orderId;
    private OrderStatus status;

    public Order(String orderId) {
        this.orderId = orderId;
        this.status = OrderStatus.NEW;
        System.out.println("Создан заказ с ID: " + orderId + " и статусом: " + status);
    }

    public void changeStatus(OrderStatus newStatus) {
        if (status == OrderStatus.DELIVERED && newStatus == OrderStatus.CANCELLED) {
            System.out.println("Невозможно отменить доставленный заказ!");
        } else {
            status = newStatus;
            System.out.println("Статус заказа изменен на: " + status);
        }
    }

    public OrderStatus getStatus() {
        return status;
    }
}

public class  Singleton{
    public static String getSeasonName(Season season) {
        switch (season) {
            case WINTER:
                return "Зима";
            case SPRING:
                return "Весна";
            case SUMMER:
                return "Лето";
            case AUTUMN:
                return "Осень";
            default:
                return "Неизвестный сезон";
        }
    }
    

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Задача 1: Singleton для подключения к базе данных
        System.out.println("Введите любое значение для создания подключения к базе данных:");
        scanner.nextLine();
        DatabaseConnection connection1 = DatabaseConnection.getInstance();
        connection1.connect();

        // Задача 2: Логирование
        Logger logger = Logger.getInstance();
        System.out.println("Введите сообщение для лога (или 'exit' для выхода):");
        String input;
        while (!(input = scanner.nextLine()).equalsIgnoreCase("exit")) {
            logger.log(input);
        }
        logger.displayLogs();

        // Задача 3: Работа со статусами заказа
        Order order = new Order("12345");
        order.changeStatus(OrderStatus.IN_PROGRESS);
        order.changeStatus(OrderStatus.DELIVERED);
        order.changeStatus(OrderStatus.CANCELLED);

        // Задача 4: Работа с Enum сезонов года
        System.out.println("Введите сезон (WINTER, SPRING, SUMMER, AUTUMN):");
        try {
            Season season = Season.valueOf(scanner.nextLine().toUpperCase());
            System.out.println("Название сезона на русском: " + getSeasonName(season));
        } catch (IllegalArgumentException e) {
            System.out.println("Некорректное значение сезона.");
        }
    }
}