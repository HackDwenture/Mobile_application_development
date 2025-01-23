import 'dart:io';
import 'dart:math';

// 1. Функция для нахождения максимума
int findMax(int a, int b) {
  if (a == b) {
    throw Exception("Числа равны");
  }
  return a > b ? a : b;
}

// 2. Калькулятор деления
double divide(double a, double b) {
  if (b == 0) {
    throw ArithmeticException("Деление на ноль недопустимо");
  }
  return a / b;
}

// 3. Конвертер строк в числа
int stringToInt(String str) {
  try {
    return int.parse(str);
  } on FormatException {
    throw NumberFormatException("Невозможно преобразовать строку в число");
  }
}

// 4. Проверка возраста
void checkAge(int age) {
  if (age < 0 || age > 150) {
    throw IllegalArgumentException("Некорректный возраст");
  }
}

// 5. Нахождение корня
double findSquareRoot(double number) {
  if (number < 0) {
    throw IllegalArgumentException("Нельзя извлечь корень из отрицательного числа");
  }
  return sqrt(number);
}

// 6. Факториал
int factorial(int n) {
  if (n < 0) {
    throw Exception("Факториал не определен для отрицательных чисел");
  }
  if (n == 0) {
    return 1;
  }
  int result = 1;
  for (int i = 1; i <= n; i++) {
    result *= i;
  }
  return result;
}


// 7. Проверка массива на нули
void checkArrayForZeros(List<int> arr) {
  if (arr.contains(0)) {
    throw Exception("Массив содержит нули");
  }
}

// 8. Калькулятор возведения в степень
double power(double base, int exponent) {
  if (exponent < 0) {
    throw Exception("Степень должна быть неотрицательной");
  }
  return pow(base, exponent).toDouble();
}

// 9. Обрезка строки
String trimString(String str, int length) {
  if (length > str.length) {
    throw Exception("Длина обрезки больше длины строки");
  }
  return str.substring(0, length);
}

// 10. Поиск элемента в массиве
int findElementInArray(List<int> arr, int element) {
  int index = arr.indexOf(element);
  if (index == -1) {
    throw Exception("Элемент не найден в массиве");
  }
  return index;
}

// 11. Конвертация в двоичную систему
String toBinary(int number) {
  if (number < 0) {
    throw Exception("Нельзя конвертировать отрицательное число в двоичное");
  }
  return number.toRadixString(2);
}

// 12. Проверка делимости
bool isDivisible(int dividend, int divisor) {
  if (divisor == 0) {
    throw ArithmeticException("Деление на ноль недопустимо");
  }
  return dividend % divisor == 0;
}

// 13. Чтение элемента списка
int getElementFromList(List<int> list, int index) {
  if (index < 0 || index >= list.length) {
    throw IndexOutOfBoundsException("Индекс выходит за границы списка");
  }
  return list[index];
}

// 14. Парольная проверка
void checkPassword(String password) {
  if (password.length < 8) {
    throw WeakPasswordException("Пароль слишком короткий");
  }
}

// 15. Проверка даты
void checkDate(String dateString) {
  RegExp regExp = RegExp(r'^\d{2}\.\d{2}\.\d{4}$');
    if (!regExp.hasMatch(dateString)){
      throw DateTimeParseException("Неверный формат даты");
    }
  try {
    DateTime.parse(dateString.split('.').reversed.join('-'));
  } on FormatException {
      throw DateTimeParseException("Неверный формат даты");
  }
}

// 16. Конкатенация строк
String concatenateStrings(String? str1, String? str2) {
  if (str1 == null || str2 == null) {
    throw NullPointerException("Одна из строк равна null");
  }
  return str1 + str2;
}

// 17. Остаток от деления
int remainder(int dividend, int divisor) {
  if (divisor == 0) {
    throw Exception("Деление на ноль недопустимо");
  }
  return dividend % divisor;
}

// 18. Квадратный корень
double squareRoot(double number) {
  if (number < 0) {
    throw Exception("Нельзя извлечь корень из отрицательного числа");
  }
  return sqrt(number);
}

// 19. Конвертер температуры
double celsiusToFahrenheit(double celsius) {
  if (celsius < -273.15) {
    throw Exception("Температура ниже абсолютного нуля");
  }
  return (celsius * 9 / 5) + 32;
}

// 20. Проверка строки на пустоту
void checkStringNotEmpty(String? str) {
  if (str == null || str.isEmpty) {
    throw Exception("Строка пустая или равна null");
  }
}

// Пользовательский интерфейс
void main() {
  while (true) {
    print("\nВыберите задачу:");
    print("1. Нахождение максимума");
    print("2. Калькулятор деления");
    print("3. Конвертер строк в числа");
    print("4. Проверка возраста");
    print("5. Нахождение корня");
    print("6. Факториал");
    print("7. Проверка массива на нули");
    print("8. Калькулятор возведения в степень");
    print("9. Обрезка строки");
    print("10. Поиск элемента в массиве");
    print("11. Конвертация в двоичную систему");
    print("12. Проверка делимости");
    print("13. Чтение элемента списка");
    print("14. Парольная проверка");
    print("15. Проверка даты");
    print("16. Конкатенация строк");
    print("17. Остаток от деления");
    print("18. Квадратный корень");
    print("19. Конвертер температуры");
    print("20. Проверка строки на пустоту");
    print("0. Выход");

    stdout.write("Введите номер задачи: ");
    String? input = stdin.readLineSync();
      if(input == null){
      print("Некорректный ввод");
      continue;
    }
    int choice = int.tryParse(input) ?? -1;

    try {
      switch (choice) {
          case 1:
          stdout.write("Введите первое число: ");
          int? a = int.tryParse(stdin.readLineSync() ?? "");
          stdout.write("Введите второе число: ");
          int? b = int.tryParse(stdin.readLineSync() ?? "");
          if (a == null || b == null) {
             print("Некорректный ввод");
            break;
          }
          print("Максимум: ${findMax(a, b)}");
          break;
        case 2:
          stdout.write("Введите делимое: ");
          double? a = double.tryParse(stdin.readLineSync() ?? "");
          stdout.write("Введите делитель: ");
          double? b = double.tryParse(stdin.readLineSync() ?? "");
          if (a == null || b == null) {
            print("Некорректный ввод");
            break;
          }
          print("Результат: ${divide(a, b)}");
          break;
        case 3:
          stdout.write("Введите строку: ");
          String? str = stdin.readLineSync();
          if (str == null) {
            print("Некорректный ввод");
            break;
          }
          print("Число: ${stringToInt(str)}");
          break;
        case 4:
          stdout.write("Введите возраст: ");
          int? age = int.tryParse(stdin.readLineSync() ?? "");
           if (age == null) {
            print("Некорректный ввод");
            break;
          }
          checkAge(age);
          print("Возраст корректен");
          break;
        case 5:
          stdout.write("Введите число: ");
          double? num = double.tryParse(stdin.readLineSync() ?? "");
          if (num == null) {
            print("Некорректный ввод");
            break;
          }
          print("Корень: ${findSquareRoot(num)}");
          break;
        case 6:
          stdout.write("Введите число для вычисления факториала: ");
          int? n = int.tryParse(stdin.readLineSync() ?? "");
           if (n == null) {
            print("Некорректный ввод");
            break;
          }
          print("Факториал: ${factorial(n)}");
          break;
        case 7:
          stdout.write("Введите числа массива через пробел: ");
          String? inputArr = stdin.readLineSync();
          if(inputArr == null){
            print("Некорректный ввод");
            break;
          }
          List<int> arr = inputArr.split(' ').map((e) => int.tryParse(e) ?? -1).toList();
          if (arr.contains(-1)){
            print("Некорректный ввод");
            break;
          }
          checkArrayForZeros(arr);
          print("Массив не содержит нулей");
          break;
        case 8:
          stdout.write("Введите основание: ");
          double? base = double.tryParse(stdin.readLineSync() ?? "");
          stdout.write("Введите степень: ");
          int? exponent = int.tryParse(stdin.readLineSync() ?? "");
           if (base == null || exponent == null) {
            print("Некорректный ввод");
            break;
          }
          print("Результат: ${power(base, exponent)}");
          break;
        case 9:
          stdout.write("Введите строку: ");
          String? str = stdin.readLineSync();
          stdout.write("Введите длину обрезки: ");
          int? length = int.tryParse(stdin.readLineSync() ?? "");
          if (str == null || length == null) {
            print("Некорректный ввод");
            break;
          }
          print("Обрезанная строка: ${trimString(str, length)}");
          break;
         case 10:
          stdout.write("Введите числа массива через пробел: ");
          String? inputArr = stdin.readLineSync();
            if(inputArr == null){
            print("Некорректный ввод");
            break;
          }
          List<int> arr = inputArr.split(' ').map((e) => int.tryParse(e) ?? -1).toList();
          if (arr.contains(-1)){
            print("Некорректный ввод");
            break;
          }
          stdout.write("Введите элемент для поиска: ");
          int? element = int.tryParse(stdin.readLineSync() ?? "");
           if (element == null) {
            print("Некорректный ввод");
            break;
          }
          print("Индекс элемента: ${findElementInArray(arr, element)}");
          break;
        case 11:
          stdout.write("Введите целое число: ");
          int? num = int.tryParse(stdin.readLineSync() ?? "");
          if (num == null) {
            print("Некорректный ввод");
            break;
          }
          print("Двоичное представление: ${toBinary(num)}");
          break;
        case 12:
          stdout.write("Введите делимое: ");
          int? dividend = int.tryParse(stdin.readLineSync() ?? "");
          stdout.write("Введите делитель: ");
          int? divisor = int.tryParse(stdin.readLineSync() ?? "");
          if (dividend == null || divisor == null) {
            print("Некорректный ввод");
          break;
          }
          if (isDivisible(dividend, divisor)) {
            print("Число делится");
          } else {
            print("Число не делится");
          }
          break;
        case 13:
          stdout.write("Введите числа списка через пробел: ");
          String? inputList = stdin.readLineSync();
          if(inputList == null){
            print("Некорректный ввод");
            break;
          }
          List<int> list = inputList.split(' ').map((e) => int.tryParse(e) ?? -1).toList();
          if (list.contains(-1)){
            print("Некорректный ввод");
            break;
          }
          stdout.write("Введите индекс элемента: ");
          int? index = int.tryParse(stdin.readLineSync() ?? "");
          if (index == null) {
            print("Некорректный ввод");
            break;
          }
          print("Элемент: ${getElementFromList(list, index)}");
          break;
        case 14:
          stdout.write("Введите пароль: ");
          String? password = stdin.readLineSync();
          if(password == null){
            print("Некорректный ввод");
            break;
          }
          checkPassword(password);
          print("Пароль надежный");
          break;
        case 15:
          stdout.write("Введите дату в формате dd.MM.yyyy: ");
          String? date = stdin.readLineSync();
            if(date == null){
            print("Некорректный ввод");
            break;
          }
          checkDate(date);
          print("Дата корректна");
          break;
        case 16:
          stdout.write("Введите первую строку: ");
          String? str1 = stdin.readLineSync();
          stdout.write("Введите вторую строку: ");
          String? str2 = stdin.readLineSync();
          print("Результат: ${concatenateStrings(str1, str2)}");
          break;
        case 17:
          stdout.write("Введите делимое: ");
          int? dividend = int.tryParse(stdin.readLineSync() ?? "");
          stdout.write("Введите делитель: ");
          int? divisor = int.tryParse(stdin.readLineSync() ?? "");
          if (dividend == null || divisor == null) {
            print("Некорректный ввод");
            break;
          }
          print("Остаток: ${remainder(dividend, divisor)}");
          break;
        case 18:
          stdout.write("Введите число: ");
          double? num = double.tryParse(stdin.readLineSync() ?? "");
           if (num == null) {
            print("Некорректный ввод");
            break;
          }
          print("Квадратный корень: ${squareRoot(num)}");
          break;
        case 19:
          stdout.write("Введите температуру в Цельсиях: ");
          double? celsius = double.tryParse(stdin.readLineSync() ?? "");
           if (celsius == null) {
            print("Некорректный ввод");
            break;
          }
          print("Температура в Фаренгейтах: ${celsiusToFahrenheit(celsius)}");
          break;
        case 20:
          stdout.write("Введите строку: ");
          String? str = stdin.readLineSync();
          checkStringNotEmpty(str);
          print("Строка не пустая");
          break;
        case 0:
          print("Выход из программы.");
          return;
        default:
          print("Некорректный выбор. Пожалуйста, выберите номер задачи из списка.");
      }
    } on Exception catch (e) {
      print("Ошибка: ${e.toString()}");
    }
  }
}


class ArithmeticException implements Exception {
  final String message;
  ArithmeticException(this.message);

    @override
  String toString() {
    return 'ArithmeticException: $message';
  }
}

class NumberFormatException implements Exception {
    final String message;
  NumberFormatException(this.message);
    @override
  String toString() {
    return 'NumberFormatException: $message';
  }
}

class IllegalArgumentException implements Exception {
    final String message;
  IllegalArgumentException(this.message);
   @override
  String toString() {
    return 'IllegalArgumentException: $message';
  }
}

class IndexOutOfBoundsException implements Exception{
    final String message;
  IndexOutOfBoundsException(this.message);

    @override
  String toString() {
    return 'IndexOutOfBoundsException: $message';
  }
}

class WeakPasswordException implements Exception {
    final String message;
  WeakPasswordException(this.message);

    @override
  String toString() {
    return 'WeakPasswordException: $message';
  }
}

class DateTimeParseException implements Exception {
    final String message;
  DateTimeParseException(this.message);

    @override
  String toString() {
    return 'DateTimeParseException: $message';
  }
}
class NullPointerException implements Exception {
    final String message;
  NullPointerException(this.message);

      @override
  String toString() {
    return 'NullPointerException: $message';
  }
}