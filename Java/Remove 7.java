import java.util.*;

public class Remove {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Введи строку с повторяющимися словами: ");
        String input = sc.nextLine();
        String[] words = input.split(" ");
        Set<String> uniqWords = new LinkedHashSet<>(Arrays.asList(words));
        StringBuilder result = new StringBuilder();
        for (String word : uniqWords) {
            result.append(word).append(" ");
        }
        
        System.out.println("Строки удалены: " + result.toString().trim());
    }
}
