/**
 * Practical 13 - Part A (Problem 1)
 * Generic Box<T>, Pair<A, B> and generic method demonstration.
 */
public class GenericContainers {

    public static class Box<T> {
        private T content;

        public Box() {}

        public Box(T content) {
            this.content = content;
        }

        public T get() {
            return content;
        }

        public void set(T content) {
            this.content = content;
        }

        @Override
        public String toString() {
            return "Box[" + content + "]";
        }
    }

    public static class Pair<A, B> {
        private final A first;
        private final B second;

        public Pair(A first, B second) {
            this.first = first;
            this.second = second;
        }

        public A getFirst() {
            return first;
        }

        public B getSecond() {
            return second;
        }

        @Override
        public String toString() {
            return "(" + first + ", " + second + ")";
        }
    }

    // Generic method that returns the first element of any array
    public static <T> T getFirstElement(T[] array) {
        if (array == null || array.length == 0) {
            return null;
        }
        return array[0];
    }

    public static void main(String[] args) {
        System.out.println("=== Generic Containers & Methods ===");

        // 1. Box<T> with String and Integer
        Box<String> stringBox = new Box<>("Antigravity IDE");
        Box<Integer> integerBox = new Box<>(2026);

        String str = stringBox.get(); // Type-safe, no cast needed!
        int num = integerBox.get();   // Auto-unboxed, no cast needed!

        System.out.println("String Box: " + str);
        System.out.println("Integer Box: " + num);

        // 2. Pair<A, B> with different types
        Pair<String, Double> productPrice = new Pair<>("Mechanical Keyboard", 3499.00);
        Pair<Integer, Boolean> responseStatus = new Pair<>(200, true);

        System.out.println("\nPair 1 (Product, Price): " + productPrice);
        System.out.println("Pair 2 (Status Code, OK):  " + responseStatus);

        // 3. Generic Method getFirstElement
        String[] fruits = { "Apple", "Banana", "Cherry" };
        Integer[] primes = { 2, 3, 5, 7, 11 };
        Double[] temperatures = { 24.5, 26.8, 22.1 };

        System.out.println("\nFirst Fruit:       " + getFirstElement(fruits));
        System.out.println("First Prime:       " + getFirstElement(primes));
        System.out.println("First Temperature: " + getFirstElement(temperatures));
    }
}
