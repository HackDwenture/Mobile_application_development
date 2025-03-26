import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;

public class Multithreading {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("\nВыберите задачу (1-12) или 0 для выхода:");
                int task;
                
                try {
                    task = scanner.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("Ошибка: введите число от 0 до 12");
                    scanner.nextLine(); // очистка буфера сканера
                    continue;
                }
                
                if (task == 0) {
                    System.out.println("Выход из программы");
                    break;
                }
                
                switch (task) {
                    case 1 -> task1();
                    case 2 -> task2();
                    case 3 -> task3();
                    case 4 -> task4();
                    case 5 -> task5();
                    case 6 -> task6();
                    case 7 -> task7();
                    case 8 -> task8();
                    case 9 -> task9();
                    case 10 -> task10();
                    case 11 -> task11();
                    case 12 -> task12();
                    default -> System.out.println("Неверный номер задачи. Введите число от 1 до 12 или 0 для выхода");
                }
            }
        }
    }

    // Задача 1: Синхронизированный счётчик
    private static void task1() {
        class Counter {
            private int count = 0;
            private final Lock lock = new ReentrantLock();

            public void increment() {
                lock.lock();
                try {
                    count++;
                } finally {
                    lock.unlock();
                }
            }

            public int getCount() {
                return count;
            }
        }

        Counter counter = new Counter();
        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    counter.increment();
                }
            });
            threads.add(thread);
            thread.start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Поток был прерван: " + e.getMessage());
            }
        }

        System.out.println("Итоговое значение счётчика: " + counter.getCount());
    }

    // Задача 2: Потокобезопасная коллекция
    private static void task2() {
        List<Integer> numbers = new CopyOnWriteArrayList<>();
        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 1; j <= 100; j++) {
                    numbers.add(j);
                }
            });
            threads.add(thread);
            thread.start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Поток был прерван: " + e.getMessage());
            }
        }

        System.out.println("Размер списка: " + numbers.size());
        System.out.println("Первые 10 элементов: " + numbers.subList(0, Math.min(10, numbers.size())));
    }

    // Задача 3: Пул потоков
    private static void task3() {
        ExecutorService executor = Executors.newFixedThreadPool(4);

        for (int i = 0; i < 20; i++) {
            final int taskNumber = i;
            executor.execute(() -> {
                System.out.println("Задача " + taskNumber + " выполняется в потоке " + 
                                  Thread.currentThread().getName());
            });
        }

        executor.shutdown();
        try {
            if (!executor.awaitTermination(1, TimeUnit.MINUTES)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            executor.shutdownNow();
            System.err.println("Ожидание завершения было прервано: " + e.getMessage());
        }
    }

    // Задача 4: Банковские переводы
    private static void task4() {
        class BankAccount {
            private final String id;
            private int balance;
            private final Lock lock = new ReentrantLock();

            public BankAccount(String id, int balance) {
                this.id = id;
                this.balance = balance;
            }

            public void transfer(BankAccount to, int amount) {
                BankAccount first = this.id.compareTo(to.id) < 0 ? this : to;
                BankAccount second = first == this ? to : this;

                first.lock.lock();
                try {
                    second.lock.lock();
                    try {
                        if (this.balance >= amount) {
                            this.balance -= amount;
                            to.balance += amount;
                            System.out.println("Переведено " + amount + " с " + this.id + " на " + to.id);
                        } else {
                            System.out.println("Недостаточно средств на счете " + this.id);
                        }
                    } finally {
                        second.lock.unlock();
                    }
                } finally {
                    first.lock.unlock();
                }
            }

            public int getBalance() {
                return balance;
            }
        }

        BankAccount acc1 = new BankAccount("A1", 1000);
        BankAccount acc2 = new BankAccount("A2", 1000);
        BankAccount acc3 = new BankAccount("A3", 1000);

        List<Thread> threads = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < 10; j++) {
                    BankAccount from = random.nextBoolean() ? acc1 : acc2;
                    BankAccount to = from == acc1 ? acc2 : acc3;
                    from.transfer(to, random.nextInt(100) + 1);
                }
            });
            threads.add(thread);
            thread.start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Поток был прерван: " + e.getMessage());
            }
        }

        System.out.println("Баланс A1: " + acc1.getBalance());
        System.out.println("Баланс A2: " + acc2.getBalance());
        System.out.println("Баланс A3: " + acc3.getBalance());
    }

    // Задача 5: Барьер синхронизации
    private static void task5() {
        final int THREADS_COUNT = 5;
        CyclicBarrier barrier = new CyclicBarrier(THREADS_COUNT, () -> 
            System.out.println("Все потоки достигли барьера. Переход к следующей фазе."));

        for (int i = 0; i < THREADS_COUNT; i++) {
            final int threadNum = i;
            new Thread(() -> {
                try {
                    System.out.println("Поток " + threadNum + " выполняет первую фазу");
                    Thread.sleep(1000 + new Random().nextInt(1000));
                    barrier.await();

                    System.out.println("Поток " + threadNum + " выполняет вторую фазу");
                    Thread.sleep(1000 + new Random().nextInt(1000));
                    barrier.await();

                    System.out.println("Поток " + threadNum + " завершил работу");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.err.println("Поток " + threadNum + " был прерван: " + e.getMessage());
                } catch (BrokenBarrierException e) {
                    System.err.println("Барьер был сломан в потоке " + threadNum + ": " + e.getMessage());
                }
            }).start();
        }
    }

    // Задача 6: Ограниченный доступ к ресурсу
    private static void task6() {
        Semaphore semaphore = new Semaphore(2); 

        for (int i = 0; i < 5; i++) {
            final int threadNum = i;
            new Thread(() -> {
                try {
                    System.out.println("Поток " + threadNum + " пытается получить доступ");
                    semaphore.acquire();
                    System.out.println("Поток " + threadNum + " получил доступ");

                    Thread.sleep(2000);

                    System.out.println("Поток " + threadNum + " освобождает доступ");
                    semaphore.release();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.err.println("Поток " + threadNum + " был прерван: " + e.getMessage());
                }
            }).start();
        }
    }

    // Задача 7: Обработка результатов задач
    private static void task7() {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        List<Future<Long>> results = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            final int num = i + 5; 
            Callable<Long> task = () -> {
                long factorial = 1;
                for (int j = 2; j <= num; j++) {
                    factorial *= j;
                }
                return factorial;
            };
            results.add(executor.submit(task));
        }

        executor.shutdown();

        try {
            for (int i = 0; i < results.size(); i++) {
                try {
                    System.out.println("Факториал " + (i + 5) + " = " + results.get(i).get());
                } catch (ExecutionException e) {
                    System.err.println("Ошибка вычисления факториала для " + (i + 5) + ": " + e.getMessage());
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Ожидание результатов было прервано: " + e.getMessage());
            executor.shutdownNow();
        }
    }

    // Задача 8: Симуляция производственной линии
    private static void task8() {
        BlockingQueue<Integer> queue = new LinkedBlockingQueue<>(10);
        Thread producer = new Thread(() -> {
            try {
                for (int i = 1; i <= 20; i++) {
                    queue.put(i);
                    System.out.println("Произведено: " + i);
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Производитель был прерван: " + e.getMessage());
            }
        });
        Thread consumer = new Thread(() -> {
            try {
                for (int i = 1; i <= 20; i++) {
                    int value = queue.take();
                    System.out.println("Обработано: " + value);
                    Thread.sleep(300);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Потребитель был прерван: " + e.getMessage());
            }
        });

        producer.start();
        consumer.start();

        try {
            producer.join();
            consumer.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Ожидание потоков было прервано: " + e.getMessage());
        }
    }

    // Задача 9: Многопоточная сортировка
    private static void task9() {
        int[] array = new int[100];
        Random random = new Random();
        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextInt(1000);
        }

        System.out.println("Исходный массив:");
        System.out.println(Arrays.toString(array));
        int[][] parts = new int[4][];
        int partSize = array.length / 4;
        for (int i = 0; i < 4; i++) {
            int start = i * partSize;
            int end = (i == 3) ? array.length : (i + 1) * partSize;
            parts[i] = Arrays.copyOfRange(array, start, end);
        }

        ExecutorService executor = Executors.newFixedThreadPool(4);
        List<Future<int[]>> futures = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            final int[] part = parts[i];
            futures.add(executor.submit(() -> {
                Arrays.sort(part);
                return part;
            }));
        }

        int[] sortedArray = new int[array.length];
        int pos = 0;
        try {
            for (Future<int[]> future : futures) {
                int[] sortedPart = future.get();
                System.arraycopy(sortedPart, 0, sortedArray, pos, sortedPart.length);
                pos += sortedPart.length;
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Ожидание сортировки было прервано: " + e.getMessage());
            executor.shutdownNow();
        } catch (ExecutionException e) {
            System.err.println("Ошибка при сортировке: " + e.getMessage());
            executor.shutdownNow();
        }

        executor.shutdown();
        Arrays.sort(sortedArray);
        System.out.println("Отсортированный массив:");
        System.out.println(Arrays.toString(sortedArray));
    }

    // Задача 10: Обед философов
    private static void task10() {
        class Philosopher implements Runnable {
            private final int id;
            private final Lock leftFork;
            private final Lock rightFork;
            private final Random random = new Random();

            public Philosopher(int id, Lock leftFork, Lock rightFork) {
                this.id = id;
                this.leftFork = leftFork;
                this.rightFork = rightFork;
            }

            private void think() throws InterruptedException {
                System.out.println("Философ " + id + " размышляет");
                Thread.sleep(random.nextInt(1000));
            }

            private void eat() throws InterruptedException {
                System.out.println("Философ " + id + " ест");
                Thread.sleep(random.nextInt(1000));
            }

            @Override
            public void run() {
                try {
                    while (!Thread.currentThread().isInterrupted()) {
                        think();         
                        if (id % 2 == 0) {
                            leftFork.lock();
                            try {
                                rightFork.lock();
                                try {
                                    eat();
                                } finally {
                                    rightFork.unlock();
                                }
                            } finally {
                                leftFork.unlock();
                            }
                        } else {
                            rightFork.lock();
                            try {
                                leftFork.lock();
                                try {
                                    eat();
                                } finally {
                                    leftFork.unlock();
                                }
                            } finally {
                                rightFork.unlock();
                            }
                        }
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.err.println("Философ " + id + " был прерван: " + e.getMessage());
                }
            }
        }

        final int PHILOSOPHERS_COUNT = 5;
        Lock[] forks = new ReentrantLock[PHILOSOPHERS_COUNT];
        for (int i = 0; i < PHILOSOPHERS_COUNT; i++) {
            forks[i] = new ReentrantLock();
        }

        ExecutorService executor = Executors.newFixedThreadPool(PHILOSOPHERS_COUNT);
        for (int i = 0; i < PHILOSOPHERS_COUNT; i++) {
            Lock leftFork = forks[i];
            Lock rightFork = forks[(i + 1) % PHILOSOPHERS_COUNT];
            executor.execute(new Philosopher(i, leftFork, rightFork));
        }

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Таймер был прерван: " + e.getMessage());
        }
        executor.shutdownNow();
    }

    // Задача 11: Умножение матриц
    private static void task11() {
        int size = 3;
        int[][] matrixA = new int[size][size];
        int[][] matrixB = new int[size][size];
        int[][] result = new int[size][size];
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrixA[i][j] = random.nextInt(10);
                matrixB[i][j] = random.nextInt(10);
            }
        }

        System.out.println("Матрица A:");
        printMatrix(matrixA);
        System.out.println("Матрица B:");
        printMatrix(matrixB);
        ExecutorService executor = Executors.newFixedThreadPool(size);

        for (int i = 0; i < size; i++) {
            final int row = i;
            executor.execute(() -> {
                for (int j = 0; j < size; j++) {
                    for (int k = 0; k < size; k++) {
                        result[row][j] += matrixA[row][k] * matrixB[k][j];
                    }
                }
            });
        }

        executor.shutdown();
        try {
            if (!executor.awaitTermination(1, TimeUnit.MINUTES)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            executor.shutdownNow();
            System.err.println("Ожидание умножения матриц было прервано: " + e.getMessage());
        }

        System.out.println("Результат умножения:");
        printMatrix(result);
    }

    private static void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            System.out.println(Arrays.toString(row));
        }
    }

    // Задача 12: Таймер с управлением
    private static void task12() {
        class TimerTask implements Runnable {
            private volatile boolean running = true;

            public void stop() {
                running = false;
            }

            @Override
            public void run() {
                long startTime = System.currentTimeMillis();
                while (running && !Thread.currentThread().isInterrupted()) {
                    long elapsed = (System.currentTimeMillis() - startTime) / 1000;
                    System.out.println("Прошло " + elapsed + " секунд");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
                System.out.println("Таймер остановлен");
            }
        }

        TimerTask timerTask = new TimerTask();
        Thread timerThread = new Thread(timerTask);
        timerThread.start();
        new Thread(() -> {
            try {
                Thread.sleep(10000);
                timerTask.stop();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Поток остановки таймера был прерван: " + e.getMessage());
            }
        }).start();
    }
}