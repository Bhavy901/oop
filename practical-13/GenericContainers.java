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

    public static <T> T getFirstElement(T[] array) {
        if (array == null || array.length == 0) {
            return null;
        }
        return array[0];
    }

    public static void main(String[] args) {
        System.out.println("=== Generic Containers & Methods ===");

        Box<String> stringBox = new Box<>("Antigravity IDE");
        Box<Integer> integerBox = new Box<>(2026);

        String str = stringBox.get();
        int num = integerBox.get();

        System.out.println("String Box: " + str);
        System.out.println("Integer Box: " + num);

        Pair<String, Double> productPrice = new Pair<>("Mechanical Keyboard", 3499.00);
        Pair<Integer, Boolean> responseStatus = new Pair<>(200, true);

        System.out.println("\nPair 1 (Product, Price): " + productPrice);
        System.out.println("Pair 2 (Status Code, OK):  " + responseStatus);

        String[] fruits = { "Apple", "Banana", "Cherry" };
        Integer[] primes = { 2, 3, 5, 7, 11 };
        Double[] temperatures = { 24.5, 26.8, 22.1 };

        System.out.println("\nFirst Fruit:       " + getFirstElement(fruits));
        System.out.println("First Prime:       " + getFirstElement(primes));
        System.out.println("First Temperature: " + getFirstElement(temperatures));
    }
}
