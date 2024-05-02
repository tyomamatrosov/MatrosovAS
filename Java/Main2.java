import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("имя:");
        String name = scanner.nextLine();
        greet(name);

    }
    public static void greet(String name) {
        String first = name.substring(0, 1).toUpperCase();
        String rest = name.substring(1).toLowerCase();
        System.out.println("Привет, " + first + rest + "!");
    }

}
