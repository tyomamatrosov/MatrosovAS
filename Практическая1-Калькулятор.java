import java.util.Scanner;
import java.util.Stack;
import java.util.Queue;
import java.util.LinkedList;
class CalculatorModel {
    public double evaluate(String expression) {
        return new ExpressionParser().parse(expression);
    }
}
class CalculatorView {
    public void showResult(double result) {
        System.out.println("Результат: " + result);
    }

    public String getInput() {
        System.out.print("Введите: ");
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
        String input = view.getInput();
        double result = model.evaluate(input);
        view.showResult(result);
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
    public double parse(String expression) {
        return evaluatePostfix(infixToPostfix(expression));
    }
    private Queue<String> infixToPostfix(String expression) {
        Queue<String> output = new LinkedList<>();
        Stack<Character> stack = new Stack<>();
        StringBuilder numberBuffer = new StringBuilder();
        boolean lastWasOperator = true;

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            if (Character.isDigit(c) || c == '.') {
                numberBuffer.append(c);
                lastWasOperator = false;
            } else {
                if (numberBuffer.length() > 0) {
                    output.add(numberBuffer.toString());
                    numberBuffer.setLength(0);
                }
                if (c == '(') {
                    stack.push(c);
                    lastWasOperator = true;
                } else if (c == ')') {
                    while (!stack.isEmpty() && stack.peek() != '(') {
                        output.add(String.valueOf(stack.pop()));
                    }
                    stack.pop();
                    lastWasOperator = false;
                } else if (isOperator(c)) {
                    if (c == '-' && lastWasOperator) {
                        numberBuffer.append(c);
                    } else {
                        while (!stack.isEmpty() && precedence(stack.peek()) >= precedence(c)) {
                            output.add(String.valueOf(stack.pop()));
                        }
                        stack.push(c);
                        lastWasOperator = true;
                    }
                }
            }
        }
        if (numberBuffer.length() > 0) {
            output.add(numberBuffer.toString());
        }
        while (!stack.isEmpty()) {
            output.add(String.valueOf(stack.pop()));
        }
        return output;
    }
    private double evaluatePostfix(Queue<String> postfix) {
        Stack<Double> stack = new Stack<>();
        while (!postfix.isEmpty()) {
            String token = postfix.poll();
            if (isNumber(token)) {
                stack.push(Double.parseDouble(token));
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
    private int precedence(char op) {
        return switch (op) {
            case '+', '-' -> 1;
            case '*', '/' -> 2;
            case '^' -> 3;
            default -> -1;
        };
    }
    private double applyOperator(double a, double b, char op) {
        return switch (op) {
            case '+' -> a + b;
            case '-' -> a - b;
            case '*' -> a * b;
            case '/' -> a / b;
            case '^' -> Math.pow(a, b);
            default -> 0;
        };
    }
}
