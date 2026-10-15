import java.util.concurrent.atomic.AtomicLong;

public class ArraySumRace {

    private static final int ARRAY_SIZE = 1_000_000;
    private static final int NUM_THREADS = 4;

    private static long unsafeTotal = 0;

    private static long synchronizedTotal = 0;
    private static final Object lock = new Object();

    private static final AtomicLong atomicTotal = new AtomicLong(0);

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Parallel Array Sum Race & Fix Comparison ===");

        int[] numbers = new int[ARRAY_SIZE];
        long expectedSum = 0;
        for (int i = 0; i < ARRAY_SIZE; i++) {
            numbers[i] = 1;
            expectedSum += numbers[i];
        }
        System.out.println("Array Size: " + ARRAY_SIZE + " elements. Expected Sum: " + expectedSum);
        System.out.println("Threads: " + NUM_THREADS + "\n");

        int chunkSize = ARRAY_SIZE / NUM_THREADS;

        Thread[] threadsUnsafe = new Thread[NUM_THREADS];
        unsafeTotal = 0;
        long startUnsafe = System.nanoTime();
        for (int t = 0; t < NUM_THREADS; t++) {
            final int start = t * chunkSize;
            final int end = (t == NUM_THREADS - 1) ? ARRAY_SIZE : (t + 1) * chunkSize;
            threadsUnsafe[t] = new Thread(() -> {
                for (int i = start; i < end; i++) {
                    unsafeTotal += numbers[i];
                }
            });
            threadsUnsafe[t].start();
        }
        for (Thread t : threadsUnsafe) t.join();
        long timeUnsafe = System.nanoTime() - startUnsafe;

        System.out.println("1. Unsynchronized Total: " + unsafeTotal + " (Expected: " + expectedSum + ")");
        System.out.println("   Lost: " + (expectedSum - unsafeTotal) + " | Time: " + (timeUnsafe / 1_000_000.0) + " ms\n");

        Thread[] threadsSync = new Thread[NUM_THREADS];
        synchronizedTotal = 0;
        long startSync = System.nanoTime();
        for (int t = 0; t < NUM_THREADS; t++) {
            final int start = t * chunkSize;
            final int end = (t == NUM_THREADS - 1) ? ARRAY_SIZE : (t + 1) * chunkSize;
            threadsSync[t] = new Thread(() -> {
                for (int i = start; i < end; i++) {
                    synchronized (lock) {
                        synchronizedTotal += numbers[i];
                    }
                }
            });
            threadsSync[t].start();
        }
        for (Thread t : threadsSync) t.join();
        long timeSync = System.nanoTime() - startSync;

        System.out.println("2. Fix 1 (Synchronized) Total: " + synchronizedTotal + " (Accurate: " + (synchronizedTotal == expectedSum) + ")");
        System.out.println("   Time: " + (timeSync / 1_000_000.0) + " ms\n");

        Thread[] threadsAtomic = new Thread[NUM_THREADS];
        atomicTotal.set(0);
        long startAtomic = System.nanoTime();
        for (int t = 0; t < NUM_THREADS; t++) {
            final int start = t * chunkSize;
            final int end = (t == NUM_THREADS - 1) ? ARRAY_SIZE : (t + 1) * chunkSize;
            threadsAtomic[t] = new Thread(() -> {
                for (int i = start; i < end; i++) {
                    atomicTotal.addAndGet(numbers[i]);
                }
            });
            threadsAtomic[t].start();
        }
        for (Thread t : threadsAtomic) t.join();
        long timeAtomic = System.nanoTime() - startAtomic;

        System.out.println("3. Fix 2 (AtomicLong) Total:  " + atomicTotal.get() + " (Accurate: " + (atomicTotal.get() == expectedSum) + ")");
        System.out.println("   Time: " + (timeAtomic / 1_000_000.0) + " ms\n");

        long[] localSums = new long[NUM_THREADS];
        Thread[] threadsLocal = new Thread[NUM_THREADS];
        long startLocal = System.nanoTime();
        for (int t = 0; t < NUM_THREADS; t++) {
            final int threadIdx = t;
            final int start = t * chunkSize;
            final int end = (t == NUM_THREADS - 1) ? ARRAY_SIZE : (t + 1) * chunkSize;
            threadsLocal[t] = new Thread(() -> {
                long localSum = 0;
                for (int i = start; i < end; i++) {
                    localSum += numbers[i];
                }
                localSums[threadIdx] = localSum;
            });
            threadsLocal[t].start();
        }
        for (Thread t : threadsLocal) t.join();
        long combinedSum = 0;
        for (long s : localSums) combinedSum += s;
        long timeLocal = System.nanoTime() - startLocal;

        System.out.println("4. Optimized (Local Sum + Reduce) Total: " + combinedSum + " (Accurate: " + (combinedSum == expectedSum) + ")");
        System.out.println("   Time: " + (timeLocal / 1_000_000.0) + " ms (Fastest, zero lock contention!)\n");
    }
}
