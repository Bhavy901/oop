package com.minibank.concurrency;

import com.minibank.model.Command;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Practical 10 (Part B)
 * Shared transaction buffer coordinating Producer and Consumer threads with wait() and notify().
 */
public class TransactionBuffer {

    private final Queue<Command> queue = new LinkedList<>();
    private final int capacity;

    public TransactionBuffer(int capacity) {
        this.capacity = capacity;
    }

    public synchronized void produce(Command cmd) throws InterruptedException {
        while (queue.size() == capacity) {
            wait();
        }
        queue.add(cmd);
        notifyAll();
    }

    public synchronized Command consume() throws InterruptedException {
        while (queue.isEmpty()) {
            wait();
        }
        Command cmd = queue.remove();
        notifyAll();
        return cmd;
    }

    public synchronized int size() {
        return queue.size();
    }
}
