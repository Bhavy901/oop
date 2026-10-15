public class BoundedStats {

    public static <N extends Number> double average(N[] values) {
        if (values == null || values.length == 0) {
            return 0.0;
        }

        double sum = 0.0;
        for (N val : values) {
            if (val != null) {
                sum += val.doubleValue();
            }
        }
        return sum / values.length;
    }

    public static void main(String[] args) {
        System.out.println("=== Bounded Generic Statistics (<N extends Number>) ===");

        Integer[] intValues = { 10, 20, 30, 40, 50 };
        Double[] doubleValues = { 10.5, 20.25, 30.75, 45.0 };
        Long[] longValues = { 1000L, 2000L, 3500L, 7500L };
        Float[] floatValues = { 1.5f, 2.5f, 5.0f };

        System.out.printf("Average of Integers: %.2f%n", average(intValues));
        System.out.printf("Average of Doubles:  %.2f%n", average(doubleValues));
        System.out.printf("Average of Longs:    %.2f%n", average(longValues));
        System.out.printf("Average of Floats:   %.2f%n", average(floatValues));

        System.out.println("\nWhy does <N extends Number> refuse non-Number types?");
        System.out.println("-> Passing a String[] array (e.g. average(new String[]{\"a\", \"b\"})) causes a compile-time error:");
        System.out.println("   'String does not implement/extend java.lang.Number', ensuring strict type-safety and preventing runtime ClassCastException!");
    }
}
