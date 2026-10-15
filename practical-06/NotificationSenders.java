/**
 * Practical 6 - Part A (Problem 2)
 * Notification senders using functional interfaces, lambdas, and marker interfaces.
 */
public class NotificationSenders {

    // Functional interface
    @FunctionalInterface
    public interface Notifier {
        void send(String message);
    }

    // Marker interface (interface with no methods)
    public interface Urgent {}

    // Wrapper class to attach marker interface to any Notifier
    public static class UrgentNotifierWrapper implements Notifier, Urgent {
        private final Notifier delegate;
        private final String name;

        public UrgentNotifierWrapper(String name, Notifier delegate) {
            this.name = name;
            this.delegate = delegate;
        }

        @Override
        public void send(String message) {
            delegate.send(message);
        }

        public String getName() {
            return name;
        }
    }

    // Standard wrapper
    public static class StandardNotifierWrapper implements Notifier {
        private final Notifier delegate;
        private final String name;

        public StandardNotifierWrapper(String name, Notifier delegate) {
            this.name = name;
            this.delegate = delegate;
        }

        @Override
        public void send(String message) {
            delegate.send(message);
        }

        public String getName() {
            return name;
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Broadcast Notification Service ===");

        // Lambda expressions for sending notifications
        Notifier emailSender = msg -> System.out.println(" [EMAIL] Sending message via SMTP: " + msg);
        Notifier smsSender = msg -> System.out.println(" [SMS]   Sending message via Telecom Gateway: " + msg);
        Notifier pushSender = msg -> System.out.println(" [PUSH]  Sending push alert to mobile devices: " + msg);

        // Mark SMS and Push as Urgent using marker interface wrapper
        Notifier[] notifiers = {
            new StandardNotifierWrapper("Standard Email", emailSender),
            new UrgentNotifierWrapper("Critical Security SMS", smsSender),
            new UrgentNotifierWrapper("Urgent Push Notification", pushSender)
        };

        String alertMessage = "CRITICAL: Server temperature exceeded threshold!";
        System.out.println("Broadcasting message: \"" + alertMessage + "\"\n");

        for (Notifier notifier : notifiers) {
            if (notifier instanceof Urgent) {
                System.out.println("-> Detected [Urgent] priority sender! Sending TWICE:");
                notifier.send(alertMessage);
                notifier.send(alertMessage);
            } else {
                System.out.println("-> Standard priority sender. Sending ONCE:");
                notifier.send(alertMessage);
            }
            System.out.println();
        }
    }
}
