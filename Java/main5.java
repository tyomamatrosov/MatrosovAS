import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введи 3 числа:");
        int startNum = scanner.nextInt();
        int stepNum = scanner.nextInt();
        int step = scanner.nextInt();
        printSeries(startNum, stepNum, step);

        scanner.close();
    }
    
    public static void printSeries(int startNum, int stepNum, int step) {
        for (int i = 0; i < step; i++) {
            System.out.print(startNum + stepNum * i + " ");
        }
        System.out.println();
    }
}
