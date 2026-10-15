import java.util.Scanner;

/**
 * Practical 8 - Part A (Problem 1)
 * Guarded Calculator with custom checked exception, multiple catch blocks, and finally block logging.
 */
public class GuardedCalculator {

    // Custom Exception
    public static class DivideByZeroException extends Exception {
        public DivideByZeroException(String message) {
            super(message);
        }
    }

    public static double calculate(double num1, double num2, char op) throws DivideByZeroException {
        return switch (op) {
            case '+' -> num1 + num2;
            case '-' -> num1 - num2;
            case '*' -> num1 * num2;
            case '/' -> {
                if (num2 == 0) {
                    throw new DivideByZeroException("Cannot divide by zero!");
                }
                yield num1 / num2;
            }
            default -> throw new IllegalArgumentException("Unsupported operator: " + op);
        };
    }

    public static void main(String[] args) {
        System.out.println("=== Robust Guarded Calculator ===");
        Scanner scanner = new Scanner(System.in);
        boolean success = false;
        int attempt = 0;

        // Loop until a valid calculation succeeds
        while (!success) {
            attempt++;
            System.out.println("\n--- Attempt #" + attempt + " ---");
            try {
                System.out.print("Enter first number: ");
                if (!scanner.hasNext()) break;
                String input1 = scanner.next();
                double num1 = Double.parseDouble(input1);

                System.out.print("Enter operator (+, -, *, /): ");
                if (!scanner.hasNext()) break;
                char op = scanner.next().trim().charAt(0);

                System.out.print("Enter second number: ");
                if (!scanner.hasNext()) break;
                String input2 = scanner.next();
                double num2 = Double.parseDouble(input2);

                double result = calculate(num1, num2, op);
                System.out.printf("SUCCESS: %.2f %c %.2f = %.2f%n", num1, op, num2, result);
                success = true;

            } catch (NumberFormatException e) {
                System.out.println("Input Error: Invalid numeric input entered. Please enter valid numbers.");
            } catch (DivideByZeroException e) {
                System.out.println("Math Error: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("Operator Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Unexpected error: " + e.getMessage());
            } finally {
                System.out.println("[AUDIT LOG] Attempt #" + attempt + " concluded. Result state: " + (success ? "RESOLVED" : "RETRY_REQUIRED"));
            }
        }
        scanner.close();
    }
}
