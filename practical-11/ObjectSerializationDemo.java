import java.io.*;
import java.util.Arrays;

public class ObjectSerializationDemo {

    public static class UserProfile implements Serializable {
        private static final long serialVersionUID = 1L;

        private final String username;
        private final String email;

        private transient String temporarySessionToken;

        public UserProfile(String username, String email, String temporarySessionToken) {
            this.username = username;
            this.email = email;
            this.temporarySessionToken = temporarySessionToken;
        }

        public String getUsername() {
            return username;
        }

        public String getEmail() {
            return email;
        }

        public String getTemporarySessionToken() {
            return temporarySessionToken;
        }

        @Override
        public String toString() {
            return "UserProfile{username='" + username + "', email='" + email +
                    "', temporarySessionToken='" + temporarySessionToken + "'}";
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Object Serialization & Transient Field Demo ===");
        File file = new File("user_profiles.dat");

        UserProfile[] originalUsers = {
            new UserProfile("alice", "alice@example.com", "TOKEN_SECRET_987"),
            new UserProfile("bob", "bob@example.com", "TOKEN_SECRET_654")
        };

        System.out.println("\nOriginal Objects before serialization:");
        for (UserProfile u : originalUsers) {
            System.out.println("  " + u);
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(originalUsers);
            System.out.println("\nSuccessfully serialized array of UserProfiles to: " + file.getName());
        } catch (IOException e) {
            System.err.println("Serialization error: " + e.getMessage());
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            UserProfile[] loadedUsers = (UserProfile[]) ois.readObject();

            System.out.println("\nDeserialized Objects from file:");
            for (UserProfile u : loadedUsers) {
                System.out.println("  " + u);
            }

            System.out.println("\nObservations:");
            System.out.println("  - Username & Email: Successfully preserved!");
            System.out.println("  - temporarySessionToken: " + loadedUsers[0].getTemporarySessionToken() +
                    " (transient field was skipped and restored as null)");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Deserialization error: " + e.getMessage());
        } finally {
            if (file.exists()) {
                file.delete();
            }
        }
    }
}
