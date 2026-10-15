import java.util.Objects;

/**
 * Practical 3 - Part A (Problem 3 - For Advanced Learners – home)
 * Fraction class reduced to lowest terms, overriding toString, equals, and hashCode.
 */
public class Fraction {

    // (a) Fields
    private final int num;
    private final int den;

    // Greatest Common Divisor helper
    private static int gcd(int a, int b) {
        a = Math.abs(a);
        b = Math.abs(b);
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a == 0 ? 1 : a;
    }

    // (b) Constructor reduces to lowest terms
    public Fraction(int num, int den) {
        if (den == 0) {
            throw new IllegalArgumentException("Denominator cannot be zero.");
        }
        int g = gcd(num, den);
        int sign = (den < 0) ? -1 : 1;
        this.num = sign * (num / g);
        this.den = sign * (den / g);
    }

    public int getNum() {
        return num;
    }

    public int getDen() {
        return den;
    }

    // (c) Override toString() to return num + "/" + den
    @Override
    public String toString() {
        return num + "/" + den;
    }

    // (d) Override equals() and hashCode() using reduced num and den
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Fraction other = (Fraction) obj;
        return this.num == other.num && this.den == other.den;
    }

    @Override
    public int hashCode() {
        return Objects.hash(num, den);
    }

    // (e) Driver
    public static void main(String[] args) {
        System.out.println("=== Equivalent Fractions Test ===");
        Fraction f1 = new Fraction(1, 2);
        Fraction f2 = new Fraction(2, 4);
        Fraction f3 = new Fraction(3, 6);
        Fraction f4 = new Fraction(5, 10);
        Fraction f5 = new Fraction(3, 4);

        System.out.println("new Fraction(1, 2) -> " + f1);
        System.out.println("new Fraction(2, 4) -> " + f2);
        System.out.println("new Fraction(3, 6) -> " + f3);
        System.out.println("new Fraction(5, 10) -> " + f4);
        System.out.println("new Fraction(3, 4) -> " + f5);

        System.out.println("\nEquality checks:");
        System.out.println("f1.equals(f2): " + f1.equals(f2)); // true
        System.out.println("f2.equals(f3): " + f2.equals(f3)); // true
        System.out.println("f1.equals(f5): " + f1.equals(f5)); // false

        System.out.println("\nHash code checks:");
        System.out.println("f1.hashCode(): " + f1.hashCode());
        System.out.println("f2.hashCode(): " + f2.hashCode());
        System.out.println("f3.hashCode(): " + f3.hashCode());
    }
}
