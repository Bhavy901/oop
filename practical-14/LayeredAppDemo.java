import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Practical 14 - Part A (Problem 1)
 * Tiny Layered Architecture (Model -> Service -> UI/Test Runner) with assertions.
 */
public class LayeredAppDemo {

    // 1. Model Layer
    public static class Task {
        private final int id;
        private final String title;
        private boolean completed;

        public Task(int id, String title) {
            if (title == null || title.trim().isEmpty()) {
                throw new IllegalArgumentException("Task title cannot be empty");
            }
            this.id = id;
            this.title = title;
            this.completed = false;
        }

        public int getId() { return id; }
        public String getTitle() { return title; }
        public boolean isCompleted() { return completed; }
        public void setCompleted(boolean completed) { this.completed = completed; }

        @Override
        public String toString() {
            return "[" + (completed ? "X" : " ") + "] #" + id + ": " + title;
        }
    }

    // 2. Service Layer
    public static class TaskService {
        private final List<Task> tasks = new ArrayList<>();
        private int idCounter = 100;

        public Task addTask(String title) {
            if (title == null || title.trim().isEmpty()) {
                throw new IllegalArgumentException("Invalid task title!");
            }
            Task task = new Task(++idCounter, title);
            tasks.add(task);
            return task;
        }

        public Task completeTask(int id) {
            for (Task t : tasks) {
                if (t.getId() == id) {
                    t.setCompleted(true);
                    return t;
                }
            }
            throw new IllegalArgumentException("Task with id #" + id + " not found!");
        }

        public List<Task> getAllTasks() {
            return Collections.unmodifiableList(tasks);
        }

        public int getTaskCount() {
            return tasks.size();
        }
    }

    // 3. Unit Test Runner (reproducing @Test behavior)
    public static void runSelfTests() {
        System.out.println("--- Running Unit Tests ---");
        TaskService service = new TaskService();

        // Test 1: Adding a task increases the count
        int initialCount = service.getTaskCount();
        service.addTask("Complete OOP Practical File");
        if (service.getTaskCount() == initialCount + 1) {
            System.out.println("  [PASS] testAddTaskIncreasesCount");
        } else {
            System.err.println("  [FAIL] testAddTaskIncreasesCount");
        }

        // Test 2: Invalid task action throws exception
        boolean caughtException = false;
        try {
            service.addTask("   "); // Invalid title
        } catch (IllegalArgumentException e) {
            caughtException = true;
        }
        if (caughtException) {
            System.out.println("  [PASS] testAddInvalidTaskThrowsException");
        } else {
            System.err.println("  [FAIL] testAddInvalidTaskThrowsException");
        }

        // Test 3: Completing an unknown task throws exception
        caughtException = false;
        try {
            service.completeTask(9999); // Unknown id
        } catch (IllegalArgumentException e) {
            caughtException = true;
        }
        if (caughtException) {
            System.out.println("  [PASS] testCompleteNonExistentTaskThrows");
        } else {
            System.err.println("  [FAIL] testCompleteNonExistentTaskThrows");
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Tiny Layered Application Demonstration ===");

        // Run automated test suite
        runSelfTests();

        System.out.println("\n--- Layered Workflow Execution ---");
        TaskService taskService = new TaskService();
        taskService.addTask("Study Generics & Reflection");
        taskService.addTask("Write MiniBank Capstone Code");
        Task t3 = taskService.addTask("Submit Practical Report");

        System.out.println("Initial Task List:");
        taskService.getAllTasks().forEach(t -> System.out.println("  " + t));

        System.out.println("\nCompleting Task #" + t3.getId() + "...");
        taskService.completeTask(t3.getId());

        System.out.println("\nUpdated Task List:");
        taskService.getAllTasks().forEach(t -> System.out.println("  " + t));
    }
}
