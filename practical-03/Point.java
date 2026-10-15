import java.util.Objects;

public class Point {

    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Point other = (Point) obj;
        return this.x == other.x && this.y == other.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public static void main(String[] args) {
        System.out.println("=== Distinct Points Evaluation ===");

        Point[] points = {
            new Point(1, 2),
            new Point(3, 4),
            new Point(1, 2),
            new Point(5, 6),
            new Point(3, 4)
        };

        System.out.println("Points array:");
        for (Point p : points) {
            System.out.println("  " + p);
        }

        int distinctCount = 0;
        for (int i = 0; i < points.length; i++) {
            boolean alreadySeen = false;
            for (int j = 0; j < i; j++) {
                if (points[i].equals(points[j])) {
                    alreadySeen = true;
                    break;
                }
            }
            if (!alreadySeen) {
                distinctCount++;
            }
        }

        System.out.println("Total points: " + points.length);
        System.out.println("Distinct: " + distinctCount);
    }
}
