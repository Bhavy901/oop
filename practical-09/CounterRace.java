public class CounterRace {

    public static class UnsafeCounter {
        private int count = 0;

        public void increment() {
            count++;
        }

        public int getCount() {
            return count;
        }
    }

    public static class SafeCounter {
        private int count = 0;

        public synchronized void increment() {
            count++;
        }

        public synchronized int getCount() {
            return count;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Multithreaded Counter Race Demonstration ===");
        final int numThreads = 10;
        final int incrementsPerThread = 10_000;
        final int expectedTotal = numThreads * incrementsPerThread;

        System.out.println("Configuration: " + numThreads + " threads, " + incrementsPerThread + " increments each.");
        System.out.println("Expected final count: " + expectedTotal + "\n");

        UnsafeCounter unsafeCounter = new UnsafeCounter();
        Thread[] unsafeThreads = new Thread[numThreads];
        for (int i = 0; i < numThreads; i++) {
            unsafeThreads[i] = new Thread(() -> {
                for (int j = 0; j < incrementsPerThread; j++) {
                    unsafeCounter.increment();
                }
            });
            unsafeThreads[i].start();
        }

        for (Thread t : unsafeThreads) {
            t.join();
        }
        System.out.println("1. Unsynchronized Counter Result: " + unsafeCounter.getCount());
        System.out.println("   Lost updates: " + (expectedTotal - unsafeCounter.getCount()) + " (Race Condition occurred!)\n");

        SafeCounter safeCounter = new SafeCounter();
        Thread[] safeThreads = new Thread[numThreads];
        for (int i = 0; i < numThreads; i++) {
            safeThreads[i] = new Thread(() -> {
                for (int j = 0; j < incrementsPerThread; j++) {
                    safeCounter.increment();
                }
            });
            safeThreads[i].start();
        }

        for (Thread t : safeThreads) {
            t.join();
        }
        System.out.println("2. Synchronized Counter Result:   " + safeCounter.getCount());
        System.out.println("   Matches Expected Total exactly: " + (safeCounter.getCount() == expectedTotal));
    }
}
