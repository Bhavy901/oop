import java.util.HashMap;
import java.util.Map;

/**
 * Practical 8 - Part A (Problem 2)
 * Warehouse stock issue processing with custom checked exceptions and recovery.
 */
public class StockWarehouse {

    // Custom checked exception carrying shortfall
    public static class OutOfStockException extends Exception {
        private final int shortfall;

        public OutOfStockException(String item, int requested, int available) {
            super("Out of stock for item '" + item + "'. Requested: " + requested + ", Available: " + available);
            this.shortfall = requested - available;
        }

        public int getShortfall() {
            return shortfall;
        }
    }

    // Custom checked exception for non-positive quantities
    public static class InvalidQuantityException extends Exception {
        public InvalidQuantityException(String message) {
            super(message);
        }
    }

    public static class Warehouse {
        private final Map<String, Integer> inventory = new HashMap<>();

        public void addItem(String item, int qty) {
            inventory.put(item.toLowerCase(), inventory.getOrDefault(item.toLowerCase(), 0) + qty);
        }

        public void issue(String item, int qty) throws OutOfStockException, InvalidQuantityException {
            if (qty <= 0) {
                throw new InvalidQuantityException("Invalid quantity requested (" + qty + "). Quantity must be positive.");
            }

            String key = item.toLowerCase();
            int currentStock = inventory.getOrDefault(key, 0);

            if (qty > currentStock) {
                throw new OutOfStockException(item, qty, currentStock);
            }

            inventory.put(key, currentStock - qty);
            System.out.println("-> Successfully issued " + qty + " units of '" + item + "'. Remaining stock: " + (currentStock - qty));
        }

        public int getStock(String item) {
            return inventory.getOrDefault(item.toLowerCase(), 0);
        }
    }

    public record StockRequest(String item, int quantity) {}

    public static void main(String[] args) {
        System.out.println("=== Warehouse Stock Issue Processing ===");
        Warehouse warehouse = new Warehouse();
        warehouse.addItem("Laptops", 10);
        warehouse.addItem("Monitors", 5);
        warehouse.addItem("Keyboards", 20);

        StockRequest[] requests = {
            new StockRequest("Laptops", 3),     // Valid
            new StockRequest("Monitors", 8),    // OutOfStock (shortfall = 3)
            new StockRequest("Keyboards", -5),  // InvalidQuantity
            new StockRequest("Laptops", 7),     // Valid (uses remaining)
            new StockRequest("Laptops", 2),     // OutOfStock (shortfall = 2)
            new StockRequest("Keyboards", 15)   // Valid
        };

        for (StockRequest req : requests) {
            System.out.println("\nProcessing request: " + req.quantity() + " units of " + req.item());
            try {
                warehouse.issue(req.item(), req.quantity());
            } catch (OutOfStockException e) {
                System.out.println("  [STOCK REJECTED] " + e.getMessage() + " -> Shortfall: " + e.getShortfall() + " units needed!");
            } catch (InvalidQuantityException e) {
                System.out.println("  [QUANTITY ERROR] " + e.getMessage());
            }
        }

        System.out.println("\n=== Final Warehouse Stock State ===");
        System.out.println("Laptops:   " + warehouse.getStock("Laptops"));
        System.out.println("Monitors:  " + warehouse.getStock("Monitors"));
        System.out.println("Keyboards: " + warehouse.getStock("Keyboards"));
    }
}
