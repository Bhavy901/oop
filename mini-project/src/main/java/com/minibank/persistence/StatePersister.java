package com.minibank.persistence;

import com.minibank.model.Account;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Practical 11 (Part B)
 * State persistence engine serializing and deserializing Account instances to disk.
 */
public class StatePersister {

    public static void save(Account[] accounts, Path file) throws IOException {
        if (file.getParent() != null) {
            Files.createDirectories(file.getParent());
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(file))) {
            oos.writeObject(accounts);
        }
    }

    public static void save(List<Account> accounts, Path file) throws IOException {
        save(accounts.toArray(new Account[0]), file);
    }

    public static Account[] load(Path file) throws IOException, ClassNotFoundException {
        if (!Files.exists(file)) {
            return new Account[0];
        }
        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(file))) {
            return (Account[]) ois.readObject();
        }
    }

    public static List<Account> loadList(Path file) throws IOException, ClassNotFoundException {
        Account[] arr = load(file);
        return new ArrayList<>(List.of(arr));
    }
}
