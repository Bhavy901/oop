import java.util.Scanner;

public class TollBooth {

    public record Vehicle(String number, String type) {}

    public static void main(String[] args) {
        System.out.println("=== Automated Toll Booth System ===");

        int totalToll = 0;
        int bikeCount = 0;
        int carCount = 0;
        int truckCount = 0;

        Scanner scanner = new Scanner(System.in);
        System.out.println("Rates: bike -> 20, car -> 50, truck -> 150");
        System.out.println("Enter vehicle number (or type 'done' to finish):");

        while (true) {
            System.out.print("Enter vehicle number: ");
            if (!scanner.hasNext()) {
                break;
            }
            String number = scanner.next().trim();
            if (number.equalsIgnoreCase("done")) {
                break;
            }

            System.out.print("Enter vehicle type (bike/car/truck): ");
            if (!scanner.hasNext()) {
                break;
            }
            String type = scanner.next().trim().toLowerCase();

            Vehicle vehicle = new Vehicle(number, type);

            int toll = switch (vehicle.type()) {
                case "bike" -> {
                    bikeCount++;
                    yield 20;
                }
                case "car" -> {
                    carCount++;
                    yield 50;
                }
                case "truck" -> {
                    truckCount++;
                    yield 150;
                }
                default -> {
                    System.out.println("Unknown vehicle type: " + vehicle.type() + ". Charged 0.");
                    yield 0;
                }
            };

            totalToll += toll;
            System.out.println("Vehicle [" + vehicle.number() + " - " + vehicle.type() + "] Toll: " + toll + ". Running Total: " + totalToll);
        }

        System.out.println("\n=== Toll Summary ===");
        System.out.println("Total toll: " + totalToll);
        System.out.println("Counts: bike=" + bikeCount + ", car=" + carCount + ", truck=" + truckCount);

        String mostFrequent;
        if (bikeCount == 0 && carCount == 0 && truckCount == 0) {
            mostFrequent = "None";
        } else if (carCount >= bikeCount && carCount >= truckCount) {
            mostFrequent = "car";
        } else if (bikeCount >= carCount && bikeCount >= truckCount) {
            mostFrequent = "bike";
        } else {
            mostFrequent = "truck";
        }

        System.out.println("Most frequent: " + mostFrequent);
        scanner.close();
    }
}
