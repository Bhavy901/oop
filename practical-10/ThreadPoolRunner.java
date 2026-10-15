import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Practical 10 - Part A (Problem 1)
 * Thread Pool runner demonstrating worker thread reuse using ExecutorService.
 */
public class ThreadPoolRunner {

    public static class WorkerTask implements Runnable {
        private final int taskId;

        public WorkerTask(int taskId) {
            this.taskId = taskId;
        }

        @Override
        public void run() {
            String threadName = Thread.currentThread().getName();
            System.out.println("  [START] Task #" + taskId + " executing on thread: " + threadName);
            try {
                // Simulate variable processing work
                Thread.sleep(150);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println("  [DONE]  Task #" + taskId + " finished on thread: " + threadName);
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Fixed Thread Pool Executor (Size = 3) ===");
        System.out.println("Submitting 10 tasks to observe thread reuse...\n");

        ExecutorService pool = Executors.newFixedThreadPool(3);

        for (int i = 1; i <= 10; i++) {
            pool.submit(new WorkerTask(i));
        }

        pool.shutdown(); // Gracefully initiate shutdown

        try {
            boolean terminated = pool.awaitTermination(5, TimeUnit.SECONDS);
            if (terminated) {
                System.out.println("\nAll 10 tasks completed successfully across the 3 reusable pool threads!");
            } else {
                System.out.println("\nTimeout waiting for tasks to finish.");
            }
        } catch (InterruptedException e) {
            System.err.println("Thread pool interrupted.");
        }
    }
}
