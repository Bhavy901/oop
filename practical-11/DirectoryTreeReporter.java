import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class DirectoryTreeReporter {

    public static class FileReportVisitor extends SimpleFileVisitor<Path> {
        private final List<String> reportEntries = new ArrayList<>();
        private long totalBytes = 0;
        private int fileCount = 0;
        private int dirCount = 0;

        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
            dirCount++;
            reportEntries.add("[DIR]  " + dir.toAbsolutePath());
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
            fileCount++;
            totalBytes += attrs.size();
            String entry = String.format("  [FILE] %-30s | Size: %8d bytes | Modified: %s",
                    file.getFileName(), attrs.size(), attrs.lastModifiedTime());
            reportEntries.add(entry);
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFileFailed(Path file, IOException exc) {
            reportEntries.add("  [ERROR ACCESSING] " + file + ": " + exc.getMessage());
            return FileVisitResult.CONTINUE;
        }

        public List<String> getReportEntries() {
            return reportEntries;
        }

        public long getTotalBytes() {
            return totalBytes;
        }

        public int getFileCount() {
            return fileCount;
        }

        public int getDirCount() {
            return dirCount;
        }
    }

    public static void generateReport(Path startDir, Path reportOutput) throws IOException {
        FileReportVisitor visitor = new FileReportVisitor();
        Files.walkFileTree(startDir, visitor);

        List<String> fullReport = new ArrayList<>();
        fullReport.add("===============================================================================");
        fullReport.add("                        DIRECTORY INSPECTION AUDIT REPORT                      ");
        fullReport.add("===============================================================================");
        fullReport.add("Root Scanned Directory: " + startDir.toAbsolutePath());
        fullReport.add("");
        fullReport.addAll(visitor.getReportEntries());
        fullReport.add("");
        fullReport.add("===============================================================================");
        fullReport.add("Total Directories: " + visitor.getDirCount());
        fullReport.add("Total Files:       " + visitor.getFileCount());
        fullReport.add("Total Data Volume: " + visitor.getTotalBytes() + " bytes");
        fullReport.add("===============================================================================");

        Files.write(reportOutput, fullReport, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        System.out.println("Audit Report generated at: " + reportOutput.toAbsolutePath());
        fullReport.forEach(System.out::println);
    }

    public static void main(String[] args) {
        System.out.println("=== Directory Tree NIO Reporter ===");
        Path targetDir = Paths.get(".");
        Path reportFile = Paths.get("directory_summary_report.txt");

        try {
            generateReport(targetDir, reportFile);

            Files.deleteIfExists(reportFile);
        } catch (IOException e) {
            System.err.println("Error generating report: " + e.getMessage());
        }
    }
}
