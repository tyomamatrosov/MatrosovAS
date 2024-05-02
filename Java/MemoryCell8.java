import java.util.Scanner;

public class MemoryCell {
    private int[] numbers = new int[3];
    private int numElements = 0;

    public void insertNum(int num) {
        if (numElements < 3) {
            numbers[numElements] = num;
            numElements++;
        } else {
            int minIndex = findMinIndex();
            numbers[minIndex] = num;
        }
    }
    public int findMinIndex() {
        int minIndex = 0;
        for (int i = 1; i < numbers.length; i++) {
            if (numbers[i] < numbers[minIndex]) {
                minIndex = i;
            }
        }
        return minIndex;
    }
    public void printNumbers() {
        System.out.println("Числа в ячейке :");
        for (int i = 0; i < numElements; i++) {
            System.out.println(numbers[i]);
        }
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MemoryCell memoryCell = new MemoryCell();
        while (true) {
            System.out.print("Введите число или -1 если нужнр выйти : ");
            int number = scanner.nextInt();
            if (number == -1) {
                break;
            }
            memoryCell.insertNum(number);
            memoryCell.printNumbers();
        }
    }
}
