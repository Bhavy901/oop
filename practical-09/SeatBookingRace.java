import java.util.ArrayList;
import java.util.List;

public class SeatBookingRace {

    public static class UnsafeCinema {
        private int seatsLeft = 5;
        private int successfulBookings = 0;

        public boolean book(String userName) {
            if (seatsLeft > 0) {

                try {
                    Thread.sleep(10);
                } catch (InterruptedException ignored) {}

                seatsLeft--;
                successfulBookings++;
                System.out.println("  [UNSAFE BOOKED] " + userName + " secured a seat! Seats left: " + seatsLeft);
                return true;
            } else {
                return false;
            }
        }

        public int getSuccessfulBookings() {
            return successfulBookings;
        }

        public int getSeatsLeft() {
            return seatsLeft;
        }
    }

    public static class SafeCinema {
        private int seatsLeft = 5;
        private int successfulBookings = 0;

        public synchronized boolean book(String userName) {
            if (seatsLeft > 0) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException ignored) {}

                seatsLeft--;
                successfulBookings++;
                System.out.println("  [SAFE BOOKED] " + userName + " secured a seat! Seats left: " + seatsLeft);
                return true;
            } else {
                System.out.println("  [REJECTED] " + userName + " - House full!");
                return false;
            }
        }

        public synchronized int getSuccessfulBookings() {
            return successfulBookings;
        }

        public synchronized int getSeatsLeft() {
            return seatsLeft;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Seat Booking Race Demonstration ===");
        final int initialSeats = 5;
        final int requestingUsers = 10;

        System.out.println("Initial seats available: " + initialSeats);
        System.out.println("Concurrent users attempting to book: " + requestingUsers + "\n");

        System.out.println("--- 1. Testing Unsafe Cinema ---");
        UnsafeCinema unsafeCinema = new UnsafeCinema();
        List<Thread> unsafeThreads = new ArrayList<>();
        for (int i = 1; i <= requestingUsers; i++) {
            final String user = "User-" + i;
            Thread t = new Thread(() -> unsafeCinema.book(user));
            unsafeThreads.add(t);
            t.start();
        }
        for (Thread t : unsafeThreads) t.join();

        System.out.println("Unsafe Cinema Result:");
        System.out.println("  Total Bookings Made: " + unsafeCinema.getSuccessfulBookings() + " (Capacity was only 5!)");
        System.out.println("  Remaining Seats Counter: " + unsafeCinema.getSeatsLeft());
        if (unsafeCinema.getSuccessfulBookings() > 5) {
            System.out.println("  CRITICAL ERROR: Oversold seats due to lack of synchronization!\n");
        }

        System.out.println("\n--- 2. Testing Safe Synchronized Cinema ---");
        SafeCinema safeCinema = new SafeCinema();
        List<Thread> safeThreads = new ArrayList<>();
        for (int i = 1; i <= requestingUsers; i++) {
            final String user = "User-" + i;
            Thread t = new Thread(() -> safeCinema.book(user));
            safeThreads.add(t);
            t.start();
        }
        for (Thread t : safeThreads) t.join();

        System.out.println("\nSafe Cinema Result:");
        System.out.println("  Total Bookings Made: " + safeCinema.getSuccessfulBookings() + " (Exactly matches capacity!)");
        System.out.println("  Remaining Seats: " + safeCinema.getSeatsLeft());
    }
}
