import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

public class LogAnalyzer {

    public static void main(String[] args) throws IOException {
        System.out.println("=== Java NIO Log Analyzer ===");

        Path tempDir = Files.createTempDirectory("log_analysis_sample_");

        Path log1 = tempDir.resolve("server.log");
        Path log2 = tempDir.resolve("database.log");

        Files.write(log1, List.of(
            "INFO Server starting on port 8080",
            "WARN High memory usage detected",
            "ERROR Failed to connect to gateway",
            "INFO Connection restored"
        ));

        Files.write(log2, List.of(
            "INFO Database initialized pool size 10",
            "ERROR Query timeout on table accounts",
            "ERROR Deadlock avoided for transaction 42",
            "INFO Checkpoint reached"
        ));

        Path[] logFiles = { log1, log2 };
        String keyword = "ERROR";

        long totalLines = 0;
        long totalKeywordMatches = 0;

        System.out.println("Analyzing log files for keyword: \"" + keyword + "\"\n");

        for (Path filePath : logFiles) {
            BasicFileAttributes attrs = Files.readAttributes(filePath, BasicFileAttributes.class);
            System.out.println("File: " + filePath.getFileName());
            System.out.println("  Size: " + attrs.size() + " bytes");
            System.out.println("  Created: " + attrs.creationTime());

            long fileLines = 0;
            long fileMatches = 0;

            try (BufferedReader reader = Files.newBufferedReader(filePath)) {
                String line;
                while ((line = reader.readLine()) != null) {
                    fileLines++;
                    if (line.contains(keyword)) {
                        fileMatches++;
                        System.out.println("    [MATCH] Line " + fileLines + ": " + line);
                    }
                }
            }

            System.out.println("  Total Lines: " + fileLines + " | Keyword Matches: " + fileMatches + "\n");
            totalLines += fileLines;
            totalKeywordMatches += fileMatches;
        }

        System.out.println("=== Summary Across All Files ===");
        System.out.println("Total Lines Scanned: " + totalLines);
        System.out.println("Total Matches Found: " + totalKeywordMatches);

        for (Path f : logFiles) Files.deleteIfExists(f);
        Files.deleteIfExists(tempDir);
    }
}
