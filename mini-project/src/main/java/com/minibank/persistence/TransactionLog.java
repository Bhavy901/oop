package com.minibank.persistence;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Collections;

/**
 * Practical 11 (Part B)
 * Transaction audit logger appending operation entries using Java NIO.
 */
public class TransactionLog {

    public static synchronized void append(Path file, String line) throws IOException {
        if (file.getParent() != null) {
            Files.createDirectories(file.getParent());
        }
        Files.write(file, Collections.singletonList(line),
                StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }
}
