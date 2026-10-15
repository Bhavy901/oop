import java.util.concurrent.atomic.AtomicLong;

/**
 * Practical 9 - Part A (Problem 3 - For Advanced Learners – home)
 * Parallel Array Summing: demonstrating race conditions, fixing via synchronized vs AtomicLong,
 * and measuring performance differences.
 */
public class ArraySumRace {

    private static final int ARRAY_SIZE = 1_000_000;
    private static final int NUM_THREADS = 4;

    // Shared accumulator for unsafe run
    private static long unsafeTotal = 0;

    // Shared accumulator for synchronized run
    private static long synchronizedTotal = 0;
    private static final Object lock = new Object();

    // Shared accumulator for atomic run
    private static final AtomicLong atomicTotal = new AtomicLong(0);

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Parallel Array Sum Race & Fix Comparison ===");

        // Prepare large array of numbers: 1, 1, 1, ...
        int[] numbers = new int[ARRAY_SIZE];
        long expectedSum = 0;
        for (int i = 0; i < ARRAY_SIZE; i++) {
            numbers[i] = 1;
            expectedSum += numbers[i];
        }
        System.out.println("Array Size: " + ARRAY_SIZE + " elements. Expected Sum: " + expectedSum);
        System.out.println("Threads: " + NUM_THREADS + "\n");

        int chunkSize = ARRAY_SIZE / NUM_THREADS;

        // 1. UNSYNCHRONIZED (RACE CONDITION)
        Thread[] threadsUnsafe = new Thread[NUM_THREADS];
        unsafeTotal = 0;
        long startUnsafe = System.nanoTime();
        for (int t = 0; t < NUM_THREADS; t++) {
            final int start = t * chunkSize;
            final int end = (t == NUM_THREADS - 1) ? ARRAY_SIZE : (t + 1) * chunkSize;
            threadsUnsafe[t] = new Thread(() -> {
                for (int i = start; i < end; i++) {
                    unsafeTotal += numbers[i]; // Race condition
                }
            });
            threadsUnsafe[t].start();
        }
        for (Thread t : threadsUnsafe) t.join();
        long timeUnsafe = System.nanoTime() - startUnsafe;

        System.out.println("1. Unsynchronized Total: " + unsafeTotal + " (Expected: " + expectedSum + ")");
        System.out.println("   Lost: " + (expectedSum - unsafeTotal) + " | Time: " + (timeUnsafe / 1_000_000.0) + " ms\n");

        // 2. FIX 1: SYNCHRONIZED BLOCK
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

        // 3. FIX 2: ATOMIC LONG
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

        // 4. BONUS BEST PRACTICE: LOCAL SUM ACCUMULATION
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
