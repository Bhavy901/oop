import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LayeredAppPackagedDemo {

    public static class Note implements Serializable {
        private static final long serialVersionUID = 1L;
        private final int id;
        private final String content;
        private final long createdAt;

        public Note(int id, String content) {
            this.id = id;
            this.content = content;
            this.createdAt = System.currentTimeMillis();
        }

        public int getId() { return id; }
        public String getContent() { return content; }
        public long getCreatedAt() { return createdAt; }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Note note = (Note) o;
            return id == note.id && Objects.equals(content, note.content);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, content);
        }

        @Override
        public String toString() {
            return "Note #" + id + ": \"" + content + "\"";
        }
    }

    public static class NoteRepository {
        public static void saveToFile(List<Note> notes, Path path) throws IOException {
            try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(path))) {
                oos.writeObject(new ArrayList<>(notes));
            }
        }

        @SuppressWarnings("unchecked")
        public static List<Note> loadFromFile(Path path) throws IOException, ClassNotFoundException {
            try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(path))) {
                return (List<Note>) ois.readObject();
            }
        }
    }

    public static void testSaveAndReloadUnchanged(Path testFile) {
        System.out.println("Running test: verify Save and Reload preserves exact state...");
        List<Note> originalNotes = List.of(
            new Note(1, "Install JDK 26 and set up environment"),
            new Note(2, "Test polymorphism and generic repository"),
            new Note(3, "Persist serialized object state to disk")
        );

        try {
            NoteRepository.saveToFile(originalNotes, testFile);
            List<Note> reloadedNotes = NoteRepository.loadFromFile(testFile);

            boolean matchSize = originalNotes.size() == reloadedNotes.size();
            boolean matchElements = originalNotes.equals(reloadedNotes);

            if (matchSize && matchElements) {
                System.out.println("  [TEST PASSED] Reloaded notes match original notes with 100% fidelity!");
                for (int i = 0; i < reloadedNotes.size(); i++) {
                    System.out.println("    Verified Note " + (i + 1) + ": " + reloadedNotes.get(i));
                }
            } else {
                System.err.println("  [TEST FAILED] State mismatch after reload!");
            }
        } catch (Exception e) {
            System.err.println("  [TEST FAILED] Exception thrown: " + e.getMessage());
        } finally {
            try {
                Files.deleteIfExists(testFile);
            } catch (IOException ignored) {}
        }
    }

    public static void main(String[] args) throws IOException {
        System.out.println("=== Packaged Layered App with Persistence Test ===");
        Path tempFile = Files.createTempFile("notes_persistence_test_", ".dat");
        testSaveAndReloadUnchanged(tempFile);
    }
}
