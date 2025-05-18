package org.example;

import java.util.Scanner;
import java.util.Stack;
import java.util.Queue;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.Map;

class CalculatorModel {
    public double evaluate(String expression) throws IllegalArgumentException {
        if (!checkParentheses(expression)) {
            throw new IllegalArgumentException("Несбалансированные скобки в выражении");
        }
        return new ExpressionParser().parse(expression);
    }

    private boolean checkParentheses(String expression) {
        int balance = 0;
        for (char c : expression.toCharArray()) {
            if (c == '(') balance++;
            if (c == ')') balance--;
            if (balance < 0) return false;
        }
        return balance == 0;
    }
}

class CalculatorView {
    public void showResult(double result) {
        System.out.println("Результат: " + result);
    }

    public void showError(String message) {
        System.out.println("Ошибка: " + message);
    }

    public String getInput() {
        System.out.print("Введите уравнение: ");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}

class CalculatorController {
    private final CalculatorModel model;
    private final CalculatorView view;

    public CalculatorController(CalculatorModel model, CalculatorView view) {
        this.model = model;
        this.view = view;
    }

    public void run() {
        try {
            String input = view.getInput();
            double result = model.evaluate(input);
            view.showResult(result);
        } catch (IllegalArgumentException e) {
            view.showError(e.getMessage());
        } catch (Exception e) {
            view.showError("Некорректное выражение");
        }
    }
}

public class Main {
    public static void main(String[] args) {
        CalculatorModel model = new CalculatorModel();
        CalculatorView view = new CalculatorView();
        CalculatorController controller = new CalculatorController(model, view);
        controller.run();
    }
}

class ExpressionParser {
    private static final Map<String, Integer> FUNCTION_ARGS = new HashMap<>();
    static {
        FUNCTION_ARGS.put("log", 1);
        FUNCTION_ARGS.put("exp", 1);
    }

    public double parse(String expression) {
        return evaluatePostfix(infixToPostfix(expression));
    }

    private Queue<String> infixToPostfix(String expression) {
        Queue<String> output = new LinkedList<>();
        Stack<String> stack = new Stack<>();
        StringBuilder buffer = new StringBuilder();
        boolean lastWasOperator = true;

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            if (Character.isDigit(c) || c == '.' || (c == '-' && lastWasOperator && i < expression.length() - 1 && Character.isDigit(expression.charAt(i + 1)))) {
                buffer.append(c);
                lastWasOperator = false;
            } else if (Character.isLetter(c)) {
                buffer.append(c);
            } else {
                if (buffer.length() > 0) {
                    String token = buffer.toString();
                    if (isFunction(token)) {
                        stack.push(token);
                    } else {
                        output.add(token);
                    }
                    buffer.setLength(0);
                }

                if (c == '(') {
                    stack.push(String.valueOf(c));
                    lastWasOperator = true;
                } else if (c == ')') {
                    while (!stack.isEmpty() && !stack.peek().equals("(")) {
                        output.add(stack.pop());
                    }
                    stack.pop(); // Удаляем "("
                    if (!stack.isEmpty() && isFunction(stack.peek())) {
                        output.add(stack.pop());
                    }
                    lastWasOperator = false;
                } else if (isOperator(c) || c == '!') {
                    String op = String.valueOf(c);
                    if (c == '^' || c == '!') {
                        // Обработка правой ассоциативности для ^ и !
                        while (!stack.isEmpty() && precedence(stack.peek()) > precedence(op)) {
                            output.add(stack.pop());
                        }
                    } else {
                        while (!stack.isEmpty() && precedence(stack.peek()) >= precedence(op)) {
                            output.add(stack.pop());
                        }
                    }
                    stack.push(op);
                    lastWasOperator = true;
                }
            }
        }

        if (buffer.length() > 0) {
            output.add(buffer.toString());
        }

        while (!stack.isEmpty()) {
            output.add(stack.pop());
        }

        return output;
    }

    private double evaluatePostfix(Queue<String> postfix) {
        Stack<Double> stack = new Stack<>();

        while (!postfix.isEmpty()) {
            String token = postfix.poll();

            if (isNumber(token)) {
                stack.push(Double.parseDouble(token));
            } else if (isFunction(token)) {
                double arg = stack.pop();
                stack.push(applyFunction(token, arg));
            } else if (token.equals("!")) {
                double a = stack.pop();
                stack.push(factorial(a));
            } else {
                double b = stack.pop();
                double a = stack.pop();
                stack.push(applyOperator(a, b, token.charAt(0)));
            }
        }

        return stack.pop();
    }

    private boolean isNumber(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isOperator(char c) {
        return "+-*/^".indexOf(c) != -1;
    }

    private boolean isFunction(String str) {
        return FUNCTION_ARGS.containsKey(str);
    }

    private int precedence(String op) {
        if (op.equals("(")) return 0;
        if (op.equals("+") || op.equals("-")) return 1;
        if (op.equals("*") || op.equals("/")) return 2;
        if (op.equals("^")) return 4;
        if (op.equals("!")) return 5;
        if (isFunction(op)) return 6;
        return -1;
    }

    private double applyOperator(double a, double b, char op) {
        switch (op) {
            case '+': return a + b;
            case '-': return a - b;
            case '*': return a * b;
            case '/': return a / b;
            case '^': return Math.pow(a, b);
            default: throw new IllegalArgumentException("Неизвестный оператор: " + op);
        }
    }

    private double applyFunction(String func, double arg) {
        switch (func) {
            case "log": return Math.log(arg) / Math.log(2); // Логарифм по основанию 2
            case "exp": return Math.exp(arg); // Экспонента
            default: throw new IllegalArgumentException("Неизвестная функция: " + func);
        }
    }

    private double factorial(double n) {
        if (n < 0) throw new IllegalArgumentException("Факториал отрицательного числа не определен");
        if (n != (int)n) throw new IllegalArgumentException("Факториал определен только для целых чисел");

        int result = 1;
        for (int i = 2; i <= n; i++) {
            result *= i;
        }
        return result;
    }
}
