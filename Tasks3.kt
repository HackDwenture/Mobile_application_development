
import kotlin.math.PI
import kotlin.math.pow

fun main() {
    while (true) {
        println("\nВыберите задачу:")
        println("1. Класс \"Человек\"")
        println("2. Наследование: Класс \"Работник\" и \"Менеджер\"")
        println("3. Полиморфизм: Животные")
        println("4. Абстрактный класс \"Транспорт\"")
        println("5. Класс \"Книга\" и \"Библиотека\"")
        println("6. Инкапсуляция: Банк")
        println("7. Счетчик объектов")
        println("8. Площадь фигур")
        println("9. Наследование: Животные и их движения")
        println("10. Работа с коллекциями: Университет")
        println("11. Класс \"Магазин\"")
        println("12. Интерфейс \"Платежная система\"")
        println("13. Генерация уникальных идентификаторов")
        println("14. Класс \"Точка\" и \"Прямоугольник\"")
        println("15. Комплексные числа")
        println("16. Перегрузка операторов: Матрица")
        println("17. Создание игры с использованием ООП")
        println("18. Автоматизированная система заказов")
        println("19. Наследование: Электроника")
        println("20. Игра \"Крестики-нолики\"")
        println("0. Выход")

        print("Введите номер задачи: ")
        val choice = readLine()?.toIntOrNull()

        when (choice) {
            1 -> task1()
            2 -> task2()
            3 -> task3()
            4 -> task4()
            5 -> task5()
            6 -> task6()
            7 -> task7()
            8 -> task8()
            9 -> task9()
            10 -> task10()
            11 -> task11()
            12 -> task12()
            13 -> task13()
            14 -> task14()
            15 -> task15()
            16 -> task16()
            17 -> task17()
            18 -> task18()
            19 -> task19()
            20 -> task20()
            0 -> return
            null -> println("Неверный ввод. Пожалуйста, введите номер задачи.")
            else -> println("Неверный номер задачи.")
        }
    }
}

// 1. Класс "Человек"
fun task1() {
    println("\n----- Задача 1: Класс \"Человек\" -----")
    val person = Person("Иван", 30, "Женский")
    person.printInfo()
    person.increaseAge()
    person.printInfo()
    person.changeName("Иван Иванов")
    person.printInfo()
}

open class Person(open var name: String, open var age: Int, open var gender: String) {
    open fun printInfo() {
        println("Имя: $name, Возраст: $age, Пол: $gender")
    }

    fun increaseAge() {
        age++
    }

    fun changeName(newName: String) {
        name = newName
    }
}

// 2. Наследование: Класс "Работник" и "Менеджер"
fun task2() {
    println("\n----- Задача 2: Наследование \"Работник\" и \"Менеджер\" -----")
    val worker = Worker("Александр", 25, "Мужской", 50000)
    worker.printInfo()

    val manager = Manager("Сергей", 40, "Мужской", 100000, listOf("Александр", "Сергей"))
    manager.printInfo()
}

open class Worker(
    override var name: String,
    override var age: Int,
    override var gender: String,
    var salary: Int
) : Person(name, age, gender) {
    override fun printInfo() {
        super.printInfo()
        println("Зарплата: $salary")
    }
}

class Manager(name: String, age: Int, gender: String, salary: Int, var subordinates: List<String>) :
    Worker(name, age, gender, salary) {
    override fun printInfo() {
        super.printInfo()
        println("Подчиненные: $subordinates")
    }
}

// 3. Полиморфизм: Животные
fun task3() {
    println("\n----- Задача 3: Полиморфизм \"Животные\" -----")
    val animals: Array<Animal> = arrayOf(Dog(), Cat(), Cow())

    for (animal in animals) {
        animal.makeSound()
    }
}

interface Animal {
    fun makeSound()
}

class Dog : Animal {
    override fun makeSound() {
        println("Гав!")
    }
}

class Cat : Animal {
    override fun makeSound() {
        println("Мяу!")
    }
}

class Cow : Animal {
    override fun makeSound() {
        println("Муу!")
    }
}

// 4. Абстрактный класс "Транспорт"
fun task4() {
    println("\n----- Задача 4: Абстрактный класс \"Транспорт\" -----")
    val car = Car()
    car.move()
    val bike = Bike()
    bike.move()
}

abstract class Transport {
    abstract fun move()
}

class Car : Transport() {
    override fun move() {
        println("Машина едет")
    }
}

class Bike : Transport() {
    override fun move() {
        println("Велосипед едет")
    }
}

// 5. Класс "Книга" и "Библиотека"
fun task5() {
    println("\n----- Задача 5: Класс \"Книга\" и \"Библиотека\" -----")
    val library = Library()
    library.addBook(Book("Властелин Колец", "Дж.Р.Р. Толкин", 1954))
    library.addBook(Book("Гордость и предубеждение", "Джейн Остин", 1813))
    library.addBook(Book("1984", "Джордж Оруэлл", 1949))

    library.searchByAuthor("Джейн Остин")
    library.searchByYear(1954)
    library.searchByYear(2000)
}

class Book(var title: String, var author: String, var year: Int) {
    override fun toString(): String {
        return "Название: $title, Автор: $author, Год: $year"
    }
}

class Library {
    private val books = mutableListOf<Book>()

    fun addBook(book: Book) {
        books.add(book)
    }

    fun searchByAuthor(author: String) {
        println("Книги автора $author:")
        books.filter { it.author == author }.forEach { println(it) }
    }

    fun searchByYear(year: Int) {
        println("Книги, выпущенные в $year:")
        books.filter { it.year == year }.forEach { println(it) }
    }
}

// 6. Инкапсуляция: Банк
fun task6() {
    println("\n----- Задача 6: Инкапсуляция \"Банк\" -----")
    val account = BankAccount(1000)
    account.deposit(500)
    account.withdraw(200)
    account.withdraw(2000)
    println("Баланс: ${account.getBalance()}")
}

class BankAccount(private var balance: Int) {
    fun deposit(amount: Int) {
        if (amount > 0) {
            balance += amount
            println("Внесено: $amount, Новый баланс: $balance")
        }
        else {
            println("Неверная сумма для внесения!")
        }
    }

    fun withdraw(amount: Int) {
        if (amount > 0 && balance >= amount) {
            balance -= amount
            println("Снято: $amount, Новый баланс: $balance")
        }
        else {
            println("Неверная сумма для снятия или недостаточно средств")
        }
    }

    fun getBalance(): Int {
        return balance
    }
}

// 7. Счетчик объектов
fun task7() {
    println("\n----- Задача 7: Счетчик объектов -----")
    val counter1 = Counter()
    val counter2 = Counter()
    val counter3 = Counter()
    println("Всего объектов создано: ${Counter.getObjectCount()}")
}

class Counter {
    companion object {
        private var objectCount = 0

        fun getObjectCount(): Int {
            return objectCount
        }
    }

    init {
        objectCount++
    }
}

// 8. Площадь фигур
fun task8() {
    println("\n----- Задача 8: Площадь фигур -----")
    val circle = Circle(5.0)
    println("Площадь круга: ${circle.getArea()}")

    val rectangle = Rectangle(4.0, 6.0)
    println("Площадь прямоугольника: ${rectangle.getArea()}")
}

abstract class Shape {
    abstract fun getArea(): Double
}

class Circle(var radius: Double) : Shape() {
    override fun getArea(): Double {
        return PI * radius.pow(2.0)
    }
}

class Rectangle(var width: Double, var height: Double) : Shape() {
    override fun getArea(): Double {
        return width * height
    }
}

// 9. Наследование: Животные и их движения
fun task9() {
    println("\n----- Задача 9: Наследование \"Животные\" и их движения -----")
    val fish = Fish()
    fish.move()
    val bird = Bird()
    bird.move()
    val dog2 = Dog2()
    dog2.move()
}

open class Animal2 {
    open fun move() {
        println("Животное двигается")
    }
}

class Fish : Animal2() {
    override fun move() {
        println("Рыба плывет")
    }
}

class Bird : Animal2() {
    override fun move() {
        println("Птица летит")
    }
}

class Dog2 : Animal2() {
    override fun move() {
        println("Собака бежит")
    }
}

// 10. Работа с коллекциями: Университет
fun task10() {
    println("\n----- Задача 10: Работа с коллекциями: Университет -----")
    val university = University()
    university.addStudent(Student("Алис", "A", 90))
    university.addStudent(Student("Боб", "B", 75))
    university.addStudent(Student("Чарли", "A", 85))

    println("Сортировка по имени:")
    university.getStudentsSortedByName().forEach { println(it) }

    println("\nФильтрация по оценке > 80:")
    university.getStudentsByGrade(80).forEach { println(it) }
}

class Student(var name: String, var group: String, var grade: Int) {
    override fun toString(): String {
        return "Имя: $name, Группа: $group, Оценка: $grade"
    }
}

class University {
    private val students = mutableListOf<Student>()

    fun addStudent(student: Student) {
        students.add(student)
    }

    fun getStudentsSortedByName(): List<Student> {
        return students.sortedBy { it.name }
    }

    fun getStudentsByGrade(grade: Int): List<Student> {
        return students.filter { it.grade > grade }
    }
}

// 11. Класс "Магазин"
fun task11() {
    println("\n----- Задача 11: Класс \"Магазин\" -----")
    val store = Store()
    store.addProduct(Product("Ноутбук", 1000.0, 5))
    store.addProduct(Product("Мышь", 25.0, 20))
    store.addProduct(Product("Клавиатура", 75.0, 10))

    store.searchProductByName("Мышь")
    store.removeProductByName("Клавиатура")
    store.searchProductByName("Клавиатура")
}

class Product(var name: String, var price: Double, var quantity: Int) {
    override fun toString(): String {
        return "Название: $name, Цена: $price, Количество: $quantity"
    }
}

class Store {
    private val products = mutableListOf<Product>()

    fun addProduct(product: Product) {
        products.add(product)
    }

    fun removeProductByName(name: String) {
        if (products.removeIf { it.name == name })
            println("Продукт: $name удален")
        else
            println("Продукт: $name не найден")
    }

    fun searchProductByName(name: String) {
        val product = products.find { it.name == name }
        if (product != null) {
            println("Продукт найден: $product")
        }
        else {
            println("Продукт: $name не найден")
        }
    }
}

// 12. Интерфейс "Платежная система"
fun task12() {
    println("\n----- Задача 12: Интерфейс \"Платежная система\" -----")
    val creditCard = CreditCard()
    creditCard.pay(100.0)
    creditCard.refund(50.0)

    val paypal = PayPal()
    paypal.pay(200.0)
    paypal.refund(75.0)
}

interface PaymentSystem {
    fun pay(amount: Double)
    fun refund(amount: Double)
}

class CreditCard : PaymentSystem {
    override fun pay(amount: Double) {
        println("Оплачено $amount с помощью кредитной карты")
    }

    override fun refund(amount: Double) {
        println("Возвращено $amount с помощью кредитной карты")
    }
}

class PayPal : PaymentSystem {
    override fun pay(amount: Double) {
        println("Оплачено $amount с помощью PayPal")
    }

    override fun refund(amount: Double) {
        println("Возвращено $amount с помощью PayPal")
    }
}

// 13. Генерация уникальных идентификаторов
fun task13() {
    println("\n----- Задача 13: Генерация уникальных идентификаторов -----")
    val uniqueID1 = UniqueID()
    println("ID 1: ${uniqueID1.getID()}")
    val uniqueID2 = UniqueID()
    println("ID 2: ${uniqueID2.getID()}")
    val uniqueID3 = UniqueID()
    println("ID 3: ${uniqueID3.getID()}")
}

class UniqueID {
    companion object {
        private var idCounter = 0
    }

    private var id: Int = 0

    init {
        id = ++idCounter
    }

    fun getID(): Int {
        return id
    }
}

// 14. Класс "Точка" и "Прямоугольник"
fun task14() {
    println("\n----- Задача 14: Класс \"Точка\" и \"Прямоугольник\" -----")
    val point1 = Point(1.0, 1.0)
    val point2 = Point(5.0, 5.0)

    val rectangle2 = Rectangle2(point1, point2)
    println("Площадь прямоугольника: ${rectangle2.getArea()}")
}

class Point(var x: Double, var y: Double)

class Rectangle2(var topLeft: Point, var bottomRight: Point) {
    fun getArea(): Double {
        val width = bottomRight.x - topLeft.x
        val height = bottomRight.y - topLeft.y
        return width * height
    }
}

// 15. Комплексные числа
fun task15() {
    println("\n----- Задача 15: Комплексные числа -----")
    val complex1 = ComplexNumber(3.0, 2.0)
    val complex2 = ComplexNumber(1.0, 5.0)

    println("Комплексное число 1: $complex1")
    println("Комплексное число 2: $complex2")

    println("Сумма: ${complex1.add(complex2)}")
    println("Разность: ${complex1.subtract(complex2)}")
    println("Произведение: ${complex1.multiply(complex2)}")
    println("Частное: ${complex1.divide(complex2)}")
}

class ComplexNumber(var real: Double, var imaginary: Double) {
    fun add(other: ComplexNumber): ComplexNumber {
        return ComplexNumber(real + other.real, imaginary + other.imaginary)
    }

    fun subtract(other: ComplexNumber): ComplexNumber {
        return ComplexNumber(real - other.real, imaginary - other.imaginary)
    }

    fun multiply(other: ComplexNumber): ComplexNumber {
        val newReal = real * other.real - imaginary * other.imaginary
        val newImaginary = real * other.imaginary + imaginary * other.real
        return ComplexNumber(newReal, newImaginary)
    }

    fun divide(other: ComplexNumber): ComplexNumber {
        val denominator = other.real * other.real + other.imaginary * other.imaginary
        val newReal = (real * other.real + imaginary * other.imaginary) / denominator
        val newImaginary = (imaginary * other.real - real * other.imaginary) / denominator
        return ComplexNumber(newReal, newImaginary)
    }

    override fun toString(): String {
        return "$real + ${imaginary}i"
    }
}

// 16. Перегрузка операторов: Матрица
fun task16() {
    println("\n----- Задача 16: Перегрузка операторов: Матрица -----")
    val matrix1 = Matrix(arrayOf(intArrayOf(1, 2), intArrayOf(3, 4)))
    val matrix2 = Matrix(arrayOf(intArrayOf(5, 6), intArrayOf(7, 8)))

    println("Матрица 1:\n$matrix1")
    println("Матрица 2:\n$matrix2")
    println("Сумма:\n${matrix1.add(matrix2)}")
    println("Произведение:\n${matrix1.multiply(matrix2)}")
}

class Matrix(var matrix: Array<IntArray>) {
    fun add(other: Matrix): Matrix {
        val result = Array(matrix.size) { IntArray(matrix[0].size) }
        for (i in matrix.indices) {
            for (j in matrix[i].indices) {
                result[i][j] = matrix[i][j] + other.matrix[i][j]
            }
        }
        return Matrix(result)
    }

    fun multiply(other: Matrix): Matrix {
        val result = Array(matrix.size) { IntArray(other.matrix[0].size) }
        for (i in matrix.indices) {
            for (j in other.matrix[0].indices) {
                for (k in matrix[0].indices) {
                    result[i][j] += matrix[i][k] * other.matrix[k][j]
                }
            }
        }
        return Matrix(result)
    }

    override fun toString(): String {
        var str = ""
        for (row in matrix) {
            str += row.joinToString(" ") + "\n"
        }
        return str
    }
}

// 17. Создание игры с использованием ООП
fun task17() {
    println("\n----- Задача 17: Создание игры с использованием ООП -----")
    val player = Player("Герой", 100, Weapon("Меч", 10))
    val enemy = Enemy("Гоблин", 50, Weapon("Дубина", 5))
    println("Изначальные характеристики:")
    player.printStats()
    enemy.printStats()

    player.attack(enemy)
    enemy.attack(player)

    println("\nПосле первой атаки:")
    player.printStats()
    enemy.printStats()
}

class Player(var name: String, var health: Int, var weapon: Weapon) {
    fun attack(enemy: Enemy) {
        println("$name атакует ${enemy.name} с помощью ${weapon.name}")
        enemy.takeDamage(weapon.damage)
    }

    fun takeDamage(damage: Int) {
        health -= damage
    }
    fun printStats(){
        println("Игрок: имя: $name, здоровье: $health, оружие: ${weapon.name}")
    }
}

class Enemy(var name: String, var health: Int, var weapon: Weapon) {
    fun attack(player: Player) {
        println("$name атакует ${player.name} с помощью ${weapon.name}")
        player.takeDamage(weapon.damage)
    }

    fun takeDamage(damage: Int) {
        health -= damage
    }
    fun printStats(){
        println("Враг: имя: $name, здоровье: $health, оружие: ${weapon.name}")
    }
}

class Weapon(var name: String, var damage: Int)

// 18. Автоматизированная система заказов
fun task18() {
    println("\n----- Задача 18: Автоматизированная система заказов -----")
    val customer = Customer("Алиса")
    val product1 = Product("Ноутбук", 1200.0, 1)
    val product2 = Product("Мышь", 25.0, 2)

    val order = Order(customer, listOf(product1, product2))
    println("Общая стоимость заказа: ${order.getTotalCost()}")

    val orderSystem = OrderSystem()
    orderSystem.addOrder(order)
    orderSystem.addOrder(Order(Customer("Боб"), listOf(Product("Клавиатура", 75.0, 1))))
    orderSystem.displayOrders()
}

class Order(var customer: Customer, var products: List<Product>) {
    fun getTotalCost(): Double {
        return products.sumOf { it.price * it.quantity }
    }

    override fun toString(): String {
        return "Клиент: ${customer.name}, продукты: $products, Итого: ${getTotalCost()}"
    }
}

class Customer(var name: String)

class OrderSystem {
    private val orders = mutableListOf<Order>()

    fun addOrder(order: Order) {
        orders.add(order)
    }

    fun displayOrders() {
        println("История заказов:")
        orders.forEach { println(it) }
    }
}

// 19. Наследование: Электроника
fun task19() {
    println("\n----- Задача 19: Наследование: Электроника -----")
    val smartphone = Smartphone("Samsung")
    smartphone.turnOn()
    smartphone.takePhoto()
    smartphone.turnOff()

    val laptop = Laptop("Apple")
    laptop.turnOn()
    laptop.runApplication()
    laptop.turnOff()
}

open class Device(var brand: String) {
    open fun turnOn() {
        println("$brand устройство включено")
    }

    open fun turnOff() {
        println("$brand устройство выключено")
    }
}

class Smartphone(brand: String) : Device(brand) {
    fun takePhoto() {
        println("Смартфон делает фото")
    }
}

class Laptop(brand: String) : Device(brand) {
    fun runApplication() {
        println("Ноутбук запускает приложение")
    }
}

// 20. Игра "Крестики-нолики"
fun task20() {
    println("\n----- Задача 20: Игра \"Крестики-нолики\" -----")
    val game = TicTacToe()
    game.playGame()
}

class TicTacToe {
    private val board = Array(3) { CharArray(3) { ' ' } }
    private var currentPlayer = 'X'

    fun playGame() {
        while (true) {
            printBoard()
            println("Ход игрока $currentPlayer.")
            val move = getPlayerMove()
            board[move.first][move.second] = currentPlayer

            if (checkWin()) {
                printBoard()
                println("Игрок $currentPlayer победил!")
                break
            }

            if (isBoardFull()) {
                printBoard()
                println("Ничья!")
                break
            }

            currentPlayer = if (currentPlayer == 'X') 'O' else 'X'
        }
    }

    private fun printBoard() {
        for (row in board) {
            println(row.joinToString("|"))
        }
        println()
    }

    private fun getPlayerMove(): Pair<Int, Int> {
        while (true) {
            print("Введите строку (0-2) и столбец (0-2) через пробел: ")
            val input = readLine()?.split(" ")
            if (input != null && input.size == 2) {
                val row = input[0].toIntOrNull()
                val col = input[1].toIntOrNull()

                if (row != null && col != null && row in 0..2 && col in 0..2 && board[row][col] == ' ') {
                    return Pair(row, col)
                }
            }
            println("Неверный ввод. Пожалуйста, введите корректную строку и столбец.")
        }
    }

    private fun checkWin(): Boolean {
        // Check rows
        for (row in board) {
            if (row[0] == currentPlayer && row[1] == currentPlayer && row[2] == currentPlayer) return true
        }

        // Check columns
        for (col in 0..2) {
            if (board[0][col] == currentPlayer && board[1][col] == currentPlayer && board[2][col] == currentPlayer) return true
        }

        // Check diagonals
        if (board[0][0] == currentPlayer && board[1][1] == currentPlayer && board[2][2] == currentPlayer) return true
        if (board[0][2] == currentPlayer && board[1][1] == currentPlayer && board[2][0] == currentPlayer) return true

        return false
    }

    private fun isBoardFull(): Boolean {
        for (row in board) {
            for (cell in row) {
                if (cell == ' ') return false
            }
        }
        return true
    }
}
