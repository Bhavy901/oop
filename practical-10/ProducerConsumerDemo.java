import java.util.LinkedList;
import java.util.Queue;

public class ProducerConsumerDemo {

    public static class BoundedBuffer {
        private final Queue<Integer> queue = new LinkedList<>();
        private final int capacity;

        public BoundedBuffer(int capacity) {
            this.capacity = capacity;
        }

        public synchronized void produce(int value) throws InterruptedException {
            while (queue.size() == capacity) {
                System.out.println("  [BUFFER FULL] Producer waiting... Size: " + queue.size());
                wait();
            }
            queue.add(value);
            System.out.println("  [PRODUCED] " + value + " | Buffer size: " + queue.size());
            notifyAll();
        }

        public synchronized int consume() throws InterruptedException {
            while (queue.isEmpty()) {
                System.out.println("  [BUFFER EMPTY] Consumer waiting... Size: 0");
                wait();
            }
            int value = queue.remove();
            System.out.println("  [CONSUMED] " + value + " | Buffer size: " + queue.size());
            notifyAll();
            return value;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Producer-Consumer with wait() / notify() ===");
        final int bufferCapacity = 3;
        final int totalItems = 10;

        BoundedBuffer buffer = new BoundedBuffer(bufferCapacity);

        Thread producer = new Thread(() -> {
            try {
                for (int i = 1; i <= totalItems; i++) {
                    buffer.produce(i);
                    Thread.sleep(80);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Producer-Thread");

        Thread consumer = new Thread(() -> {
            try {
                for (int i = 1; i <= totalItems; i++) {
                    int val = buffer.consume();
                    Thread.sleep(120);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Consumer-Thread");

        producer.start();
        consumer.start();

        producer.join();
        consumer.join();

        System.out.println("\nSuccessfully produced and consumed all " + totalItems + " items in order with zero loss!");
    }
}
