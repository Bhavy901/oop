import java.util.EmptyStackException;

public class GenericStack {

    public static class CustomStack<T> {
        private Object[] elements;
        private int size = 0;
        private static final int DEFAULT_CAPACITY = 10;

        public CustomStack() {
            elements = new Object[DEFAULT_CAPACITY];
        }

        public CustomStack(int capacity) {
            elements = new Object[Math.max(1, capacity)];
        }

        public void push(T item) {
            ensureCapacity();
            elements[size++] = item;
        }

        @SuppressWarnings("unchecked")
        public T pop() {
            if (isEmpty()) {
                throw new EmptyStackException();
            }
            T item = (T) elements[--size];
            elements[size] = null;
            return item;
        }

        @SuppressWarnings("unchecked")
        public T peek() {
            if (isEmpty()) {
                throw new EmptyStackException();
            }
            return (T) elements[size - 1];
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public int size() {
            return size;
        }

        private void ensureCapacity() {
            if (size == elements.length) {
                Object[] newArray = new Object[elements.length * 2];
                System.arraycopy(elements, 0, newArray, 0, elements.length);
                elements = newArray;
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Generic Array-backed Stack ===");

        System.out.println("\n--- 1. Testing CustomStack<String> ---");
        CustomStack<String> bookStack = new CustomStack<>(2);
        bookStack.push("Java in Action");
        bookStack.push("Effective Java");
        bookStack.push("Clean Architecture");

        System.out.println("Stack size: " + bookStack.size());
        System.out.println("Top element (peek): " + bookStack.peek());
        while (!bookStack.isEmpty()) {
            System.out.println("Popped: " + bookStack.pop());
        }

        try {
            bookStack.pop();
        } catch (EmptyStackException e) {
            System.out.println("Sensibly handled empty case: Caught EmptyStackException as expected!");
        }

        System.out.println("\n--- 2. Testing CustomStack<Integer> ---");
        CustomStack<Integer> numberStack = new CustomStack<>();
        for (int i = 10; i <= 50; i += 10) {
            numberStack.push(i);
        }

        System.out.println("Stack size: " + numberStack.size());
        System.out.println("Popping all integers:");
        while (!numberStack.isEmpty()) {
            System.out.print(numberStack.pop() + " ");
        }
        System.out.println();
    }
}
