import java.io.*;
import java.util.*;
import java.util.regex.*;

public class Calculator {
    // Метод для вычисления выражения
    public static double evaluateExpression(String expression) throws Exception {
        // Замена символов на Java-совместимые операторы
        expression = expression.replace("^", "**");

        // Обработка модуля числа |x|
        expression = processAbsoluteValue(expression);

        // Использование ScriptEngine для вычисления выражения
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("JavaScript");
        Object result = engine.eval(expression);
        return Double.parseDouble(result.toString());
    }

    // Метод для обработки модуля числа
    private static String processAbsoluteValue(String expression) {
        Pattern pattern = Pattern.compile("\\|([^|]+)\\|");
        Matcher matcher = pattern.matcher(expression);

        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            String value = matcher.group(1);
            double number = evaluateWithoutAbs(value);
            matcher.appendReplacement(sb, String.valueOf(Math.abs(number)));
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    // Метод для вычисления выражения без обработки модуля
    private static double evaluateWithoutAbs(String expression) {
        try {
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName("JavaScript");
            Object result = engine.eval(expression);
            return Double.parseDouble(result.toString());
        } catch (Exception e) {
            throw new RuntimeException("Ошибка в выражении: " + e.getMessage());
        }
    }

    // Метод для записи истории вычислений в файл
    public static void saveHistory(String expression, double result) {
        try (FileWriter fw = new FileWriter("История.txt", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(expression + " = " + result);
        } catch (IOException e) {
            System.err.println("Ошибка при записи в файл: " + e.getMessage());
        }
    }

    // Метод для чтения истории вычислений из файла
    public static List<String> readHistory() {
        List<String> history = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("История.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                history.add(line);
            }
        } catch (FileNotFoundException e) {
            history.add("История пуста.");
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
        }
        return history;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Введите выражение (или 'история' для просмотра истории, 'выход' для завершения): ");
            String userInput = scanner.nextLine();

            if (userInput.equalsIgnoreCase("выход")) {
                break;
            } else if (userInput.equalsIgnoreCase("история")) {
                List<String> history = readHistory();
                System.out.println("История вычислений:");
                for (String record : history) {
                    System.out.println(record);
                }
            } else {
                try {
                    double result = evaluateExpression(userInput);
                    System.out.println("Результат: " + result);
                    saveHistory(userInput, result);
                } catch (Exception e) {
                    System.err.println("Ошибка в выражении: " + e.getMessage());
                }
            }
        }

        scanner.close();
    }
}
