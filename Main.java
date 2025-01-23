
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nВыберите задачу (1-20) или 0 для выхода:");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 0:
                    System.out.println("Выход из программы.");
                    return;
                case 1:
                    task1(scanner);
                    break;
                case 2:
                    task2(scanner);
                    break;
                case 3:
                    task3();
                    break;
                case 4:
                    task4(scanner);
                    break;
                case 5:
                    task5(scanner);
                    break;
                case 6:
                    task6(scanner);
                    break;
                case 7:
                    task7(scanner);
                    break;
                case 8:
                    task8(scanner);
                    break;
                case 9:
                    task9(scanner);
                    break;
                case 10:
                    task10(scanner);
                    break;
                case 11:
                    task11(scanner);
                    break;
                case 12:
                    task12(scanner);
                    break;
                case 13:
                    task13(scanner);
                    break;
                case 14:
                    task14(scanner);
                    break;
                case 15:
                    task15(scanner);
                    break;
                case 16:
                   task16(scanner);
                   break;
                case 17:
                    task17(scanner);
                    break;
                case 18:
                     task18(scanner);
                     break;
                case 19:
                    task19(scanner);
                    break;
                case 20:
                    task20(scanner);
                    break;
                default:
                    System.out.println("Неверный выбор. Попробуйте еще раз.");
            }
        }
    }
    // Задача 1: Четное или нечетное число
    public static void task1(Scanner scanner) {
        System.out.println("Введите целое число:");
        int number = scanner.nextInt();
        if (number % 2 == 0) {
            System.out.println("Число " + number + " четное.");
        } else {
            System.out.println("Число " + number + " нечетное.");
        }
    }

    // Задача 2: Минимальное из трех чисел
    public static void task2(Scanner scanner) {
        System.out.println("Введите три целых числа:");
        int num1 = scanner.nextInt();
        int num2 = scanner.nextInt();
        int num3 = scanner.nextInt();
        int min = Math.min(Math.min(num1, num2), num3);
        System.out.println("Минимальное число: " + min);
    }

    // Задача 3: Таблица умножения на 5
    public static void task3() {
        System.out.println("Таблица умножения на 5:");
        for (int i = 1; i <= 10; i++) {
            System.out.println("5 * " + i + " = " + (5 * i));
        }
    }
    // Задача 4: Сумма чисел от 1 до N
    public static void task4(Scanner scanner) {
        System.out.println("Введите целое число N:");
        int n = scanner.nextInt();
        int sum = 0;
        for (int i = 1; i <= n; i++) {
            sum += i;
        }
        System.out.println("Сумма чисел от 1 до " + n + ": " + sum);
    }
    // Задача 5: Числа Фибоначчи
    public static void task5(Scanner scanner) {
        System.out.println("Введите целое число N:");
        int n = scanner.nextInt();
        if (n <= 0) {
            System.out.println("N должно быть больше 0.");
            return;
        }
        int[] fib = new int[n];
        if (n >= 1) {
            fib[0] = 0;
        }
        if (n >= 2) {
             fib[1] = 1;
        }

        for (int i = 2; i < n; i++) {
            fib[i] = fib[i - 1] + fib[i - 2];
        }
        System.out.println("Первые " + n + " чисел Фибоначчи: " + Arrays.toString(fib));
    }

    // Задача 6: Проверка простого числа
    public static void task6(Scanner scanner) {
        System.out.println("Введите целое число:");
        int number = scanner.nextInt();
        if (number <= 1) {
            System.out.println(number + " не является простым числом.");
            return;
        }
        boolean isPrime = true;
        for (int i = 2; i <= Math.sqrt(number); i++) {
            if (number % i == 0) {
                isPrime = false;
                break;
            }
        }
        if (isPrime) {
            System.out.println(number + " является простым числом.");
        } else {
            System.out.println(number + " не является простым числом.");
        }
    }

    // Задача 7: Обратный порядок чисел
    public static void task7(Scanner scanner) {
        System.out.println("Введите целое число N:");
        int n = scanner.nextInt();
        System.out.println("Числа от " + n + " до 1 в обратном порядке:");
        for (int i = n; i >= 1; i--) {
            System.out.print(i + " ");
        }
        System.out.println();
    }
    // Задача 8: Сумма четных чисел в диапазоне
    public static void task8(Scanner scanner) {
        System.out.println("Введите два целых числа A и B:");
        int a = scanner.nextInt();
        int b = scanner.nextInt();
        int sum = 0;
        for (int i = Math.min(a, b); i <= Math.max(a, b); i++) {
            if (i % 2 == 0) {
                sum += i;
            }
        }
        System.out.println("Сумма четных чисел в диапазоне от " + a + " до " + b + ": " + sum);
    }
   // Задача 9: Реверс строки
    public static void task9(Scanner scanner) {
        System.out.println("Введите строку:");
        String input = scanner.nextLine();
        String reversed = new StringBuilder(input).reverse().toString();
        System.out.println("Строка в обратном порядке: " + reversed);
    }
    // Задача 10: Количество цифр в числе
    public static void task10(Scanner scanner) {
        System.out.println("Введите целое число:");
        int number = scanner.nextInt();
        int count = 0;
        if (number == 0) {
           count = 1;
         }else{
           number = Math.abs(number);
           while (number > 0) {
              number /= 10;
             count++;
           }
         }
         System.out.println("Количество цифр в числе: " + count);
    }
    // Задача 11: Факториал числа
    public static void task11(Scanner scanner) {
        System.out.println("Введите целое число N:");
        int n = scanner.nextInt();
        long factorial = 1;
        if (n < 0) {
            System.out.println("Факториал не определен для отрицательных чисел.");
            return;
        }
        for (int i = 1; i <= n; i++) {
            factorial *= i;
        }
        System.out.println("Факториал числа " + n + ": " + factorial);
    }
     // Задача 12: Сумма цифр числа
    public static void task12(Scanner scanner) {
        System.out.println("Введите целое число:");
        int number = scanner.nextInt();
        int sum = 0;
         number = Math.abs(number);
        while (number > 0) {
            sum += number % 10;
            number /= 10;
        }
        System.out.println("Сумма цифр числа: " + sum);
    }
    // Задача 13: Палиндром
    public static void task13(Scanner scanner) {
        System.out.println("Введите строку:");
        String input = scanner.nextLine();
        String cleanInput = input.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
        String reversed = new StringBuilder(cleanInput).reverse().toString();
        if (cleanInput.equals(reversed)) {
            System.out.println("Строка является палиндромом.");
        } else {
            System.out.println("Строка не является палиндромом.");
        }
    }

    // Задача 14: Найти максимальное число в массиве
    public static void task14(Scanner scanner) {
        System.out.println("Введите размер массива:");
        int size = scanner.nextInt();
        int[] array = new int[size];
        System.out.println("Введите элементы массива:");
        for (int i = 0; i < size; i++) {
            array[i] = scanner.nextInt();
        }
        int max = array[0];
        for (int i = 1; i < size; i++) {
            if (array[i] > max) {
                max = array[i];
            }
        }
        System.out.println("Максимальное число в массиве: " + max);
    }

    // Задача 15: Сумма всех элементов массива
    public static void task15(Scanner scanner) {
        System.out.println("Введите размер массива:");
        int size = scanner.nextInt();
        int[] array = new int[size];
        System.out.println("Введите элементы массива:");
        for (int i = 0; i < size; i++) {
            array[i] = scanner.nextInt();
        }
        int sum = 0;
        for (int num : array) {
            sum += num;
        }
        System.out.println("Сумма всех элементов массива: " + sum);
    }
     // Задача 16: Количество положительных и отрицательных чисел
    public static void task16(Scanner scanner){
        System.out.println("Введите размер массива:");
        int size = scanner.nextInt();
        int[] array = new int[size];
        System.out.println("Введите элементы массива:");
        for (int i = 0; i < size; i++) {
            array[i] = scanner.nextInt();
        }
        int positiveCount = 0;
        int negativeCount = 0;
        for (int num : array) {
            if (num > 0) {
                positiveCount++;
            } else if (num < 0) {
                negativeCount++;
            }
        }
         System.out.println("Количество положительных чисел: " + positiveCount);
         System.out.println("Количество отрицательных чисел: " + negativeCount);
    }
     // Задача 17: Простые числа в диапазоне
    public static void task17(Scanner scanner) {
        System.out.println("Введите два целых числа A и B:");
        int a = scanner.nextInt();
        int b = scanner.nextInt();
        System.out.println("Простые числа в диапазоне от " + a + " до " + b + ":");
        for (int i = Math.min(a, b); i <= Math.max(a, b); i++) {
            if (isPrime(i)) {
                System.out.print(i + " ");
            }
        }
        System.out.println();
    }
    // вспомогательный метод проверки числа на простоту для задачи 17
    public static boolean isPrime(int number) {
        if (number <= 1) {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(number); i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }
    // Задача 18: Подсчет гласных и согласных в строке
    public static void task18(Scanner scanner){
          System.out.println("Введите строку:");
          String input = scanner.nextLine().toLowerCase();
          int vowelCount = 0;
          int consonantCount = 0;
          String vowels = "aeiouyаеёиоуыэюя";
         for (char c : input.toCharArray()) {
               if (Character.isLetter(c)) {
                 if (vowels.contains(String.valueOf(c))) {
                      vowelCount++;
                  } else {
                      consonantCount++;
                 }
             }
         }
        System.out.println("Количество гласных: " + vowelCount);
        System.out.println("Количество согласных: " + consonantCount);
    }
    // Задача 19: Перестановка слов в строке
    public static void task19(Scanner scanner) {
        System.out.println("Введите строку, состоящую из нескольких слов:");
        String input = scanner.nextLine();
        String[] words = input.split("\\s+"); // Разбиваем строку на слова
        System.out.println("Слова в обратном порядке:");
        for (int i = words.length - 1; i >= 0; i--) {
           System.out.print(words[i] + " ");
        }
       System.out.println();
    }
    // Задача 20: Число Армстронга
    public static void task20(Scanner scanner) {
        System.out.println("Введите целое число:");
        int number = scanner.nextInt();
        int originalNumber = number;
        int n = String.valueOf(number).length();
        int sum = 0;

        while (number > 0) {
            int digit = number % 10;
            sum += Math.pow(digit, n);
            number /= 10;
        }

        if (sum == originalNumber) {
            System.out.println(originalNumber + " является числом Армстронга.");
        } else {
            System.out.println(originalNumber + " не является числом Армстронга.");
        }
    }

}
