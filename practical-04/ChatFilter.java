import java.util.Scanner;

public class ChatFilter {

    public static void main(String[] args) {
        System.out.println("=== Chat Log Filter ===");

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

            String[] parts = logLine.split(" ", 3);
            if (parts.length < 3) {

                continue;
            }

            String time = parts[0];
            String user = parts[1];
            String message = parts[2];

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

        System.out.println("Matches: " + matchesCount);
        if (matchesCount > 0) {
            System.out.print(report.toString());
        } else {
            System.out.println("No matching messages found.");
        }

        scanner.close();
    }
}
