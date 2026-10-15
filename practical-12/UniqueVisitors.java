import java.util.*;

public class UniqueVisitors {

    public static void main(String[] args) {
        System.out.println("=== Unique Visitor Log Analyzer ===");

        List<String> rawLogins = List.of(
            "bhavy", "riya", "aarav", "bhavy", "diya", "karan", "riya", "neha", "aarav", "rohan"
        );

        System.out.println("Raw Login Stream (" + rawLogins.size() + " total records):");
        System.out.println("  " + rawLogins);

        Set<String> uniqueSet = new HashSet<>(rawLogins);
        System.out.println("\nTotal Unique Users (HashSet count): " + uniqueSet.size());

        Set<String> orderedUniqueSet = new LinkedHashSet<>(rawLogins);
        System.out.println("First-Seen Ordered Unique Visitors (LinkedHashSet):");
        System.out.println("  " + orderedUniqueSet);

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
