/**
 * Practical 2 - Part A (Problem 2)
 * Cinema Show booking and cancellation tracking.
 */
public class CinemaShow {

    // (a) Fields
    private String title;
    private int seatsAvailable;
    private final int capacity;
    private static int totalBooked = 0;

    // (b) Constructor with title and capacity
    public CinemaShow(String title, int capacity) {
        this.title = title;
        this.capacity = capacity;
        this.seatsAvailable = capacity;
    }

    // Constructor chaining with default capacity of 100
    public CinemaShow(String title) {
        this(title, 100);
    }

    // (c) book(int n)
    public boolean book(int n) {
        if (n > 0 && n <= seatsAvailable) {
            seatsAvailable -= n;
            totalBooked += n;
            return true;
        }
        return false;
    }

    // (d) cancel(int n)
    public void cancel(int n) {
        if (n > 0) {
            seatsAvailable = Math.min(capacity, seatsAvailable + n);
        }
    }

    // (e) Getters
    public String getTitle() {
        return title;
    }

    public int getSeatsAvailable() {
        return seatsAvailable;
    }

    public int getCapacity() {
        return capacity;
    }

    public static int getTotalBooked() {
        return totalBooked;
    }

    // (f) main method
    public static void main(String[] args) {
        System.out.println("=== Cinema Show Booking System ===");
        CinemaShow show = new CinemaShow("Inception", 50);

        System.out.println("Show: " + show.getTitle() + " | Capacity: " + show.getCapacity() + " | Seats Available: " + show.getSeatsAvailable());

        // Test booking 20 seats
        boolean b1 = show.book(20);
        System.out.println("Book 20 seats -> Success: " + b1 + " | Remaining seats: " + show.getSeatsAvailable());

        // Test booking 25 seats
        boolean b2 = show.book(25);
        System.out.println("Book 25 seats -> Success: " + b2 + " | Remaining seats: " + show.getSeatsAvailable());

        // Test booking 10 seats (should fail as only 5 remain)
        boolean b3 = show.book(10);
        System.out.println("Book 10 seats -> Success: " + b3 + " | Remaining seats: " + show.getSeatsAvailable());

        // Cancel 5 seats
        show.cancel(5);
        System.out.println("Cancelled 5 seats -> Remaining seats: " + show.getSeatsAvailable());

        // Cancel excess seats (should not exceed capacity)
        show.cancel(60);
        System.out.println("Cancelled 60 seats (capped at capacity) -> Remaining seats: " + show.getSeatsAvailable());

        System.out.println("\nTotal seats successfully booked across all calls: " + CinemaShow.getTotalBooked());
    }
}
