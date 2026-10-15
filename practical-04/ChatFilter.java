import java.util.Scanner;

/**
 * Practical 4 - Part A (Problem 2)
 * Chat-log filter parsing tokens with split and building reports with StringBuilder.
 */
public class ChatFilter {

    public static void main(String[] args) {
        System.out.println("=== Chat Log Filter ===");

        // (a) Sample log lines with one malformed line
        String[] logs = {
            "10:05 alice Hello there everyone",
            "10:06 bob Hi Alice",
            "malformed_line_with_no_spaces",
            "10:07 charlie Hello world from java",
            "10:08 david Good morning"
        };

        Scanner scanner = new Scanner(System.in);
        String keyword = "hello";

        System.out.println("Available logs count: " + logs.length);
        System.out.print("Enter search keyword [default: 'hello']: ");
        if (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (!line.isEmpty()) {
                keyword = line;
            }
        }

        System.out.println("Filtering logs with keyword: \"" + keyword + "\"");

        int matchesCount = 0;
        StringBuilder report = new StringBuilder();

        for (String logLine : logs) {
            // (c) Split into time/user/message with split(" ", 3)
            String[] parts = logLine.split(" ", 3);
            if (parts.length < 3) {
                // Skip lines that do not have 3 parts
                continue;
            }

            String time = parts[0];
            String user = parts[1];
            String message = parts[2];

            // (d) Case-insensitive match on message
            if (message.toLowerCase().contains(keyword.toLowerCase())) {
                matchesCount++;
                report.append(time)
                      .append(" ")
                      .append(user)
                      .append(": ")
                      .append(message)
                      .append(System.lineSeparator());
            }
        }

        // (e) Print "Matches: N" and built report
        System.out.println("Matches: " + matchesCount);
        if (matchesCount > 0) {
            System.out.print(report.toString());
        } else {
            System.out.println("No matching messages found.");
        }

        scanner.close();
    }
}
