import java.util.*;

/**
 * Practical 12 - Part A (Problem 2)
 * Tracking unique visitors using HashSet and preserving arrival order with LinkedHashSet.
 */
public class UniqueVisitors {

    public static void main(String[] args) {
        System.out.println("=== Unique Visitor Log Analyzer ===");

        // Stream of user logins with duplicates
        List<String> rawLogins = List.of(
            "bhavy", "riya", "aarav", "bhavy", "diya", "karan", "riya", "neha", "aarav", "rohan"
        );

        System.out.println("Raw Login Stream (" + rawLogins.size() + " total records):");
        System.out.println("  " + rawLogins);

        // 1. HashSet for rapid unique counting
        Set<String> uniqueSet = new HashSet<>(rawLogins);
        System.out.println("\nTotal Unique Users (HashSet count): " + uniqueSet.size());

        // 2. LinkedHashSet to preserve first-seen insertion order
        Set<String> orderedUniqueSet = new LinkedHashSet<>(rawLogins);
        System.out.println("First-Seen Ordered Unique Visitors (LinkedHashSet):");
        System.out.println("  " + orderedUniqueSet);

        // 3. Print the first three distinct names
        System.out.println("\nFirst Three Distinct Users to arrive:");
        int count = 0;
        for (String user : orderedUniqueSet) {
            System.out.println("  " + (++count) + ". " + user);
            if (count == 3) {
                break;
            }
        }
    }
}
