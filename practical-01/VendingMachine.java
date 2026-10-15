import java.util.Scanner;

public class VendingMachine {

    public enum Coin {
        ONE, TWO, FIVE, TEN
    }

    public static void main(String[] args) {
        System.out.println("=== Vending Machine Simulation ===");

        final int snackPrice = 15;
        int runningTotal = 0;

        Scanner scanner = new Scanner(System.in);
        System.out.println("Snack price is Rs. " + snackPrice);
        System.out.println("Accepted coins: ONE, TWO, FIVE, TEN");

        while (runningTotal < snackPrice) {
            System.out.print("Insert coin (ONE, TWO, FIVE, TEN): ");
            if (!scanner.hasNext()) {
                break;
            }
            String input = scanner.next().trim().toUpperCase();

            try {
                Coin coin = Coin.valueOf(input);
                int value = switch (coin) {
                    case ONE -> 1;
                    case TWO -> 2;
                    case FIVE -> 5;
                    case TEN -> 10;
                };

                runningTotal += value;
                System.out.println("Inserted: " + coin + " (" + value + " Rs). Running total: " + runningTotal + " Rs");
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid coin: " + input + ". Please insert ONE, TWO, FIVE, or TEN.");
            }
        }

        int change = runningTotal - snackPrice;
        System.out.println("Paid. Change: " + change);
        scanner.close();
    }
}
