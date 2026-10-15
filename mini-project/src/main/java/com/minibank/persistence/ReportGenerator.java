package com.minibank.persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Practical 11 (Part B)
 * Report generator inspecting transaction logs using Files.newDirectoryStream and BufferedReader.
 */
public class ReportGenerator {

    public static String generateDailyReport(Path logsDir, Path reportOutputFile) throws IOException {
        if (!Files.exists(logsDir)) {
            Files.createDirectories(logsDir);
        }

        long totalDeposits = 0;
        long totalWithdrawals = 0;
        int totalTransactions = 0;
        List<String> fileSummaries = new ArrayList<>();

        // Practical 11: Walk logs folder using Files.newDirectoryStream
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(logsDir)) {
            for (Path entry : stream) {
                // Supplementary Practical 11: Skip empty files or files that do not end with .log
                if (Files.isDirectory(entry) || !entry.getFileName().toString().endsWith(".log") || Files.size(entry) == 0) {
                    continue;
                }

                BasicFileAttributes attrs = Files.readAttributes(entry, BasicFileAttributes.class);
                fileSummaries.add(String.format("Log File: %-25s | Size: %6d bytes | Last Modified: %s",
                        entry.getFileName(), attrs.size(), attrs.lastModifiedTime()));

                try (BufferedReader reader = Files.newBufferedReader(entry)) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        line = line.trim();
                        if (line.isEmpty()) continue;

                        String[] tokens = line.split("\\s+");
                        if (tokens.length >= 3) {
                            String action = tokens[0].toUpperCase();
                            try {
                                long amount = Long.parseLong(tokens[2]);
                                if (action.contains("DEPOSIT")) {
                                    totalDeposits += amount;
                                    totalTransactions++;
                                } else if (action.contains("WITHDRAW") || action.contains("TRANSFER")) {
                                    totalWithdrawals += amount;
                                    totalTransactions++;
                                }
                            } catch (NumberFormatException ignored) {}
                        }
                    }
                }
            }
        }

        long netFlow = totalDeposits - totalWithdrawals;
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        StringBuilder sb = new StringBuilder();
        sb.append("========================================================================\n");
        sb.append("                    MINIBANK DAILY TRANSACTION AUDIT REPORT             \n");
        sb.append("========================================================================\n");
        sb.append(String.format("Generated On          : %s%n", timestamp));
        sb.append(String.format("Scanned Directory     : %s%n%n", logsDir.toAbsolutePath()));
        sb.append("--- Processed Log Files ---\n");
        if (fileSummaries.isEmpty()) {
            sb.append("  (No active non-empty .log files found in directory)\n");
        } else {
            fileSummaries.forEach(s -> sb.append("  ").append(s).append("\n"));
        }
        sb.append("\n--- Financial Reconciliation Totals ---\n");
        sb.append(String.format("Total Transactions    : %d%n", totalTransactions));
        sb.append(String.format("Total Deposits (In)   : Rs. %d%n", totalDeposits));
        sb.append(String.format("Total Withdrawals(Out): Rs. %d%n", totalWithdrawals));
        sb.append(String.format("Net Financial Flow    : Rs. %+d%n", netFlow));
        sb.append("========================================================================\n");

        String reportContent = sb.toString();

        if (reportOutputFile != null) {
            if (reportOutputFile.getParent() != null) {
                Files.createDirectories(reportOutputFile.getParent());
            }
            Files.writeString(reportOutputFile, reportContent,
                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        }

        return reportContent;
    }
}
