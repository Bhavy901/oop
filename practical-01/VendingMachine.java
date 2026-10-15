import java.util.Scanner;

/**
 * Practical 1 - Part A (Problem 1)
 * Vending Machine simulation using enum and switch expressions.
 */
public class VendingMachine {

    // (a) Define an enum Coin with constants ONE, TWO, FIVE, TEN.
    public enum Coin {
        ONE, TWO, FIVE, TEN
    }

    public static void main(String[] args) {
        System.out.println("=== Vending Machine Simulation ===");
        // (b) Snack price 15 and running total of 0
        final int snackPrice = 15;
        int runningTotal = 0;

        Scanner scanner = new Scanner(System.in);
        System.out.println("Snack price is Rs. " + snackPrice);
        System.out.println("Accepted coins: ONE, TWO, FIVE, TEN");

        // (c) & (d) Loop: read coin name, use switch expression, stop once total reaches 15 or more
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

        // (e) Print change to return
        int change = runningTotal - snackPrice;
        System.out.println("Paid. Change: " + change);
        scanner.close();
    }
}
