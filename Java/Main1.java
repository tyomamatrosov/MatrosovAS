import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите 1 строку :");
        String fS = scanner.nextLine();
        System.out.println("Введите 2 строку:");
        String sS= scanner.nextLine();
        System.out.println(checkEnding(fS, sS));
    }

    public static boolean checkEnding(String fS, String sS) {
        return fS.endsWith(sS);
    }
}
