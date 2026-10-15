public class ParkingLot {

    private int twoWheelers;
    private int fourWheelers;
    private final int twoCap;
    private final int fourCap;
    private static long revenue = 0;

    public ParkingLot(int twoCap, int fourCap) {
        this.twoCap = twoCap;
        this.fourCap = fourCap;
        this.twoWheelers = 0;
        this.fourWheelers = 0;
    }

    public boolean park(String type) {
        if ("two".equalsIgnoreCase(type)) {
            if (twoWheelers < twoCap) {
                twoWheelers++;
                revenue += 20;
                System.out.println("Parked 2-wheeler. Occupancy: " + twoWheelers + "/" + twoCap + " | Toll: 20");
                return true;
            } else {
                System.out.println("2-Wheeler section Full! Rejected.");
                return false;
            }
        } else if ("four".equalsIgnoreCase(type)) {
            if (fourWheelers < fourCap) {
                fourWheelers++;
                revenue += 40;
                System.out.println("Parked 4-wheeler. Occupancy: " + fourWheelers + "/" + fourCap + " | Toll: 40");
                return true;
            } else {
                System.out.println("4-Wheeler section Full! Rejected.");
                return false;
            }
        } else {
            System.out.println("Unknown vehicle type: " + type);
            return false;
        }
    }

    public void leave(String type) {
        if ("two".equalsIgnoreCase(type)) {
            if (twoWheelers > 0) {
                twoWheelers--;
                System.out.println("2-wheeler left. Current count: " + twoWheelers);
            }
        } else if ("four".equalsIgnoreCase(type)) {
            if (fourWheelers > 0) {
                fourWheelers--;
                System.out.println("4-wheeler left. Current count: " + fourWheelers);
            }
        }
    }

    public int getTwoWheelers() {
        return twoWheelers;
    }

    public int getFourWheelers() {
        return fourWheelers;
    }

    public static long getRevenue() {
        return revenue;
    }

    public static void main(String[] args) {
        System.out.println("=== Parking Lot Simulation ===");
        ParkingLot lot = new ParkingLot(2, 2);

        System.out.println("Capacities -> 2-Wheelers: 2, 4-Wheelers: 2");

        lot.park("two");
        lot.park("two");
        lot.park("two");

        lot.park("four");
        lot.park("four");
        lot.park("four");

        lot.leave("two");
        lot.park("two");

        System.out.println("\n=== Final Summary ===");
        System.out.println("Final 2-Wheelers: " + lot.getTwoWheelers());
        System.out.println("Final 4-Wheelers: " + lot.getFourWheelers());
        System.out.println("Total Revenue: Rs. " + ParkingLot.getRevenue());
    }
}
