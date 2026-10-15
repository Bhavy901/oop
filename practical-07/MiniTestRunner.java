import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class MiniTestRunner {

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface Run {
        String description() default "";
    }

    public static class SampleTestSuite {

        @Run(description = "Verify addition operation")
        public void testAddition() {
            int a = 10;
            int b = 20;
            if (a + b != 30) throw new AssertionError("Addition failed!");
            System.out.println("    -> testAddition executed successfully.");
        }

        public void helperMethod() {
            System.out.println("    -> helperMethod should NOT be run automatically.");
        }

        @Run(description = "Verify string concatenation")
        public void testStringConcat() {
            String s = "Hello " + "World";
            if (!s.equals("Hello World")) throw new AssertionError("Concat failed!");
            System.out.println("    -> testStringConcat executed successfully.");
        }

        @Run(description = "Verify exception handling")
        public void testDivisionByZeroHandled() {
            try {
                int x = 10 / 0;
            } catch (ArithmeticException e) {
                System.out.println("    -> testDivisionByZeroHandled caught expected exception.");
            }
        }

        private void privateNonTestMethod() {

        }
    }

    public static void runTests(Class<?> testClass) {
        System.out.println("Discovering and running @Run tests in: " + testClass.getSimpleName());
        int ranCount = 0;
        int passedCount = 0;
        int failedCount = 0;

        try {
            Object instance = testClass.getDeclaredConstructor().newInstance();
            Method[] methods = testClass.getDeclaredMethods();

            for (Method method : methods) {
                if (method.isAnnotationPresent(Run.class)) {
                    Run annotation = method.getAnnotation(Run.class);
                    ranCount++;
                    System.out.println("  [RUNNING] " + method.getName() + " (" + annotation.description() + ")");

                    try {
                        method.setAccessible(true);
                        method.invoke(instance);
                        passedCount++;
                        System.out.println("  [PASSED]  " + method.getName() + "\n");
                    } catch (Exception ex) {
                        failedCount++;
                        System.out.println("  [FAILED]  " + method.getName() + ": " + ex.getCause().getMessage() + "\n");
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Could not instantiate test class: " + e.getMessage());
        }

        System.out.println("=== Test Run Summary ===");
        System.out.println("Total tests executed: " + ranCount);
        System.out.println("Passed: " + passedCount);
        System.out.println("Failed: " + failedCount);
    }

    public static void main(String[] args) {
        System.out.println("=== Mini Test Runner (JUnit Reflection Principle) ===");
        runTests(SampleTestSuite.class);
    }
}
