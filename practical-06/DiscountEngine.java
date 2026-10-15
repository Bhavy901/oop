import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Practical 6 - Part A (Problem 3 - For Advanced Learners – home)
 * Runtime discount engine selecting lambda rules dynamically.
 */
public class DiscountEngine {

    @FunctionalInterface
    public interface DiscountRule {
        double applyDiscount(double originalPrice);
    }

    public record DiscountOption(int id, String description, DiscountRule rule) {}

    public static void main(String[] args) {
        System.out.println("=== Dynamic Retail Discount Engine ===");

        List<DiscountOption> options = new ArrayList<>();
        // Lambda 1: 10% Seasonal Percentage Off
        options.add(new DiscountOption(1, "10% Festive Season Discount", price -> price * 0.90));
        // Lambda 2: Flat Rs. 150 off on purchases above Rs. 500
        options.add(new DiscountOption(2, "Flat Rs. 150 OFF (min purchase Rs. 500)", price -> price > 500 ? price - 150 : price));
        // Lambda 3: Buy 1 Get 50% Clearance
        options.add(new DiscountOption(3, "50% Clearance Flash Sale", price -> price * 0.50));
        // Lambda 4: Student Special (20% off)
        options.add(new DiscountOption(4, "Student Special (20% OFF)", price -> price * 0.80));

        double[] cartPrices = { 250.0, 600.0, 1200.0, 4500.0, 80.0 };

        System.out.println("\nCart Items Original Prices:");
        double originalTotal = 0;
        for (double p : cartPrices) {
            System.out.printf("  Item price: Rs. %8.2f%n", p);
            originalTotal += p;
        }
        System.out.printf("Original Cart Total: Rs. %.2f%n%n", originalTotal);

        System.out.println("Select a Discount Rule to apply:");
        for (DiscountOption opt : options) {
            System.out.println("  [" + opt.id() + "] " + opt.description());
        }

        Scanner scanner = new Scanner(System.in);
        int choice = 1; // Default
        System.out.print("\nEnter discount rule number (1-4) [default: 1]: ");
        if (scanner.hasNextInt()) {
            choice = scanner.nextInt();
        }

        DiscountOption selectedOption = options.get(0);
        for (DiscountOption opt : options) {
            if (opt.id() == choice) {
                selectedOption = opt;
                break;
            }
        }

        System.out.println("\nApplying Rule: " + selectedOption.description());
        double discountedTotal = 0;
        for (double price : cartPrices) {
            double finalPrice = selectedOption.rule().applyDiscount(price);
            discountedTotal += finalPrice;
            System.out.printf("  Original: Rs. %8.2f  ==>  Discounted: Rs. %8.2f (Saved: Rs. %6.2f)%n",
                    price, finalPrice, (price - finalPrice));
        }

        System.out.printf("%nFinal Payable Cart Total: Rs. %.2f%n", discountedTotal);
        System.out.printf("Total Savings: Rs. %.2f%n", (originalTotal - discountedTotal));
        scanner.close();
    }
}
