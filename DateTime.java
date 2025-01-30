import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;

public class DateTimeTasks {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\nВыберите задачу:");
            System.out.println("1. Текущая дата и время");
            System.out.println("2. Сравнение дат");
            System.out.println("3. Дни до Нового года");
            System.out.println("4. Проверка високосного года");
            System.out.println("5. Подсчет выходных за месяц");
            System.out.println("6. Время выполнения метода");
            System.out.println("7. Форматирование и парсинг даты");
            System.out.println("8. Конвертация между часовыми поясами");
            System.out.println("9. Вычисление возраста по дате рождения");
            System.out.println("10. Календарь на месяц");
            System.out.println("11. Генерация случайной даты");
            System.out.println("12. Время до события");
            System.out.println("13. Вычисление рабочих часов");
            System.out.println("14. Формат даты с учетом локали");
            System.out.println("15. День недели по дате");
            System.out.println("0. Выход");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    showCurrentDateTime();
                    break;
                case 2:
                    compareDates();
                    break;
                case 3:
                    daysUntilNewYear();
                    break;
                case 4:
                    checkLeapYear();
                    break;
                case 5:
                    countWeekendsInMonth();
                    break;
                case 6:
                    measureMethodExecution();
                    break;
                case 7:
                    formatAndParseDate();
                    break;
                case 8:
                    convertTimeZone();
                    break;
                case 9:
                    calculateAge();
                    break;
                case 10:
                    printMonthlyCalendar();
                    break;
                case 11:
                    generateRandomDate();
                    break;
                case 12:
                    timeUntilEvent();
                    break;
                case 13:
                    calculateWorkingHours();
                    break;
                case 14:
                    formatDateWithLocale();
                    break;
                case 15:
                    dayOfWeekInRussian();
                    break;
                case 0:
                    System.out.println("Выход...");
                    return;
                default:
                    System.out.println("Неверный выбор!");
            }
        }
    }

    private static void showCurrentDateTime() {
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        System.out.println("Текущая дата: " + currentDate.format(dateFormatter));
        System.out.println("Текущее время: " + currentTime.format(timeFormatter));
    }

    private static void compareDates() {
        System.out.println("Введите первую дату (yyyy-MM-dd):");
        LocalDate date1 = LocalDate.parse(scanner.nextLine());
        System.out.println("Введите вторую дату (yyyy-MM-dd):");
        LocalDate date2 = LocalDate.parse(scanner.nextLine());

        if (date1.isAfter(date2)) {
            System.out.println("Первая дата больше второй.");
        } else if (date1.isBefore(date2)) {
            System.out.println("Первая дата меньше второй.");
        } else {
            System.out.println("Даты равны.");
        }
    }

    private static void daysUntilNewYear() {
        LocalDate today = LocalDate.now();
        LocalDate newYear = LocalDate.of(today.getYear() + 1, 1, 1);
        long daysUntil = ChronoUnit.DAYS.between(today, newYear);
        System.out.println("До Нового года осталось " + daysUntil + " дней.");
    }

    private static void checkLeapYear() {
        System.out.println("Введите год:");
        int year = scanner.nextInt();
        boolean isLeap = Year.isLeap(year);
        System.out.println(year + " високосный? " + isLeap);
    }
    private static void countWeekendsInMonth() {
        System.out.println("Введите месяц и год (MM yyyy):");
        int month = scanner.nextInt();
        int year = scanner.nextInt();
        LocalDate date = LocalDate.of(year, month, 1);
        int weekends = 0;
        while (date.getMonthValue() == month) {
            DayOfWeek day = date.getDayOfWeek();
            if (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY) {
                weekends++;
            }
            date = date.plusDays(1);
        }
        System.out.println("Количество выходных: " + weekends);
    }

    private static void measureMethodExecution() {
        long start = System.nanoTime();
        for (int i = 0; i < 1_000_000; i++) {
            Math.sqrt(i);
        }
        long end = System.nanoTime();
        System.out.println("Время выполнения: " + (end - start) + " наносекунд.");
    }

    private static void formatAndParseDate() {
        System.out.println("Введите дату в формате dd-MM-yyyy:");
        String inputDate = scanner.nextLine();
        LocalDate date = LocalDate.parse(inputDate, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        LocalDate newDate = date.plusDays(10);
        System.out.println("Новая дата: " + newDate.format(DateTimeFormatter.ofPattern("yyyy/MM/dd")));
    }

    private static void convertTimeZone() {
        System.out.println("Введите дату и время в формате yyyy-MM-dd HH:mm:ss (UTC):");
        String inputDateTime = scanner.nextLine();
        LocalDateTime dateTime = LocalDateTime.parse(inputDateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        ZonedDateTime utcDateTime = dateTime.atZone(ZoneId.of("UTC"));
        ZonedDateTime moscowDateTime = utcDateTime.withZoneSameInstant(ZoneId.of("Europe/Moscow"));
        System.out.println("Дата и время в Moscow: " + moscowDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }

    private static void calculateAge() {
        System.out.println("Введите дату рождения (yyyy-MM-dd):");
        LocalDate birthDate = LocalDate.parse(scanner.nextLine());
        int age = Period.between(birthDate, LocalDate.now()).getYears();
        System.out.println("Ваш возраст: " + age + " лет.");
    }

    private static void printMonthlyCalendar() {
        System.out.println("Введите месяц и год (MM yyyy):");
        int month = scanner.nextInt();
        int year = scanner.nextInt();
        LocalDate date = LocalDate.of(year, month, 1);
        while (date.getMonthValue() == month) {
            System.out.println(date + " - " + (date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY ? "Выходной" : "Рабочий день"));
            date = date.plusDays(1);
        }
    }

    private static void generateRandomDate() {
        System.out.println("Введите начальную дату (yyyy-MM-dd):");
        LocalDate startDate = LocalDate.parse(scanner.nextLine());
        System.out.println("Введите конечную дату (yyyy-MM-dd):");
        LocalDate endDate = LocalDate.parse(scanner.nextLine());

        long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);
        Random random = new Random();
        LocalDate randomDate = startDate.plusDays(random.nextInt((int) daysBetween + 1));
        System.out.println("Случайная дата: " + randomDate);
    }

    private static void timeUntilEvent() {
        System.out.println("Введите дату и время события (yyyy-MM-dd HH:mm:ss):");
        LocalDateTime eventDateTime = LocalDateTime.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Duration duration = Duration.between(LocalDateTime.now(), eventDateTime);
        System.out.println("До события осталось: " + duration.toHoursPart() + " часов, " + duration.toMinutesPart() + " минут, " + duration.toSecondsPart() + " секунд.");
    }
    private static void calculateWorkingHours() {
        System.out.println("Введите начало рабочего дня (yyyy-MM-dd HH:mm):");
        LocalDateTime start = LocalDateTime.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        System.out.println("Введите конец рабочего дня (yyyy-MM-dd HH:mm):");
        LocalDateTime end = LocalDateTime.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        Duration duration = Duration.between(start, end);
        System.out.println("Количество рабочих часов: " + duration.toHours());
    }

    private static void formatDateWithLocale() {
        System.out.println("Введите дату (yyyy-MM-dd):");
        LocalDate date = LocalDate.parse(scanner.nextLine());
        System.out.println("Дата в локали RU: " + date.format(DateTimeFormatter.ofPattern("dd MMMM yyyy", new Locale("ru"))));
    }

    private static void dayOfWeekInRussian() {
        System.out.println("Введите дату (yyyy-MM-dd):");
        LocalDate date = LocalDate.parse(scanner.nextLine());
        String dayOfWeek = date.getDayOfWeek().getDisplayName(java.time.format.TextStyle.FULL, new Locale("ru"));
        System.out.println("День недели: " + dayOfWeek);
    }
}