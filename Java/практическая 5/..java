import java.io.*;
import java.util.*;

public class Main {

    private static final String HISTORY_FILE = "his.txt";
    
    // Метод для сложения
    public static double summ(double a, double b) {
        return a + b;
    }

    // Метод для вычитания
    public static double minus(double a, double b) {
        return a - b;
    }

    // Метод для умножения
    public static double ymnozhenie(double a, double b) {
        return a * b;
    }

    // Метод для деления
    public static double del(double a, double b) {
        if (b == 0) {
            throw new ArithmeticException("Деление на 0");
        }
        return a / b;
    }

    // Метод для деления без остатка
    public static int delbezost(int a, int b) {
        if (b == 0) {
            throw new ArithmeticException("Деление на 0");
        }
        return a / b;
    }

    // Метод для остатка от деления
    public static int mod(int a, int b) {
        if (b == 0) {
            throw new ArithmeticException("деление на 0");
        }
        return a % b;
    }

    // Метод для возведения в степень
    public static double stepen(double a, double b) {
        return Math.pow(a, b);
    }

    // Метод для модуля числа
    public static double modul(double a) {
        return Math.abs(a);
    }

    // Метод для парсинга и вычисления выражения
    public static double evaluate(String expression) {
        // Для упрощения использования обратной польской записи (RPN)
        return new Object() {
            int pos = -1, c;

            void nextChar() {
                c = (++pos < expression.length()) ? expression.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (c == ' ') nextChar();
                if (c == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < expression.length()) throw new RuntimeException("нету: " + (char)c);
                return x;
            }

            double parseExpression() {
                double x = parseTerm();
                for (;;) {
                    if (eat('+')) x += parseTerm(); // сложение
                    else if (eat('-')) x -= parseTerm(); // вычитание
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (;;) {
                    if (eat('*')) x *= parseFactor(); // умножение
                    else if (eat('/')) x /= parseFactor(); // деление
                    else if (eat('%')) x %= parseFactor(); // остаток от деления
                    else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return parseFactor();
                if (eat('-')) return -parseFactor();

                double x;
                int startPos = this.pos;
                if (eat('(')) { // скобки
                    x = parseExpression();
                    eat(')');
                } else if ((c >= '0' && c <= '9') || c == '.') { // числа
                    while ((c >= '0' && c <= '9') || c == '.') nextChar();
                    x = Double.parseDouble(expression.substring(startPos, this.pos));
                } else if (c == '|') { // модуль
                    nextChar();
                    x = parseExpression();
                    if (!eat('|')) throw new RuntimeException("найдено '|'");
                    x = modul(x);
                } else {
                    throw new RuntimeException("ненайдено " + (char)c);
                }

                if (eat('^')) x = Math.pow(x, parseFactor()); // возведение в степень

                return x;
            }
        }.parse();
    }

    // Метод для сохранения истории вычислений
    public static void saveHistory(List<String> history) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(HISTORY_FILE))) {
            for (String record : history) {
                writer.write(record);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Метод для загрузки истории вычислений
    public static List<String> loadHistory() {
        List<String> history = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(HISTORY_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                history.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return history;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<String> history = loadHistory();

        System.out.println("Процесс запущен! Чтобы выйти пиши'exit' .");
        while (true) {
            System.out.print("Введи уравнение: ");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("exit")) {
                break;
            }
            try {
                double result = evaluate(input);
                String record = input + " = " + result;
                history.add(record);
                System.out.println(record);
                saveHistory(history);
            } catch (Exception e) {
                System.out.println("Ошибяка!: " + e.getMessage());
            }
        }
        scanner.close();
    }
}
