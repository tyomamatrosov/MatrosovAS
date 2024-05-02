import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введи строчку:");
        String input = scanner.nextLine();
        System.out.println(removeVowels(input));
    }
    
    public static String removeVowels(String input) {
        return inputString.replaceAll("[aeiouAEIOU]", "");
    }
}
