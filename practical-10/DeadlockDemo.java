public class DeadlockDemo {

    public static class Account {
        private final String id;
        private double balance;

        public Account(String id, double balance) {
            this.id = id;
            this.balance = balance;
        }

        public String getId() {
            return id;
        }

        public double getBalance() {
            return balance;
        }

        public void withdraw(double amount) {
            this.balance -= amount;
        }

        public void deposit(double amount) {
            this.balance += amount;
        }
    }

    public static void unsafeTransfer(Account from, Account to, double amount) {
        System.out.println(Thread.currentThread().getName() + " attempting to lock " + from.getId());
        synchronized (from) {
            System.out.println("  " + Thread.currentThread().getName() + " locked " + from.getId() + ", waiting for " + to.getId());
            try {
                Thread.sleep(50);
            } catch (InterruptedException ignored) {}

            synchronized (to) {
                System.out.println("    " + Thread.currentThread().getName() + " locked both " + from.getId() + " and " + to.getId() + "! Transferring " + amount);
                from.withdraw(amount);
                to.deposit(amount);
            }
        }
    }

    public static void safeTransfer(Account acc1, Account acc2, double amount, boolean from1to2) {
        Account firstLock = acc1.getId().compareTo(acc2.getId()) < 0 ? acc1 : acc2;
        Account secondLock = acc1.getId().compareTo(acc2.getId()) < 0 ? acc2 : acc1;

        synchronized (firstLock) {
            synchronized (secondLock) {
                if (from1to2) {
                    acc1.withdraw(amount);
                    acc2.deposit(amount);
                } else {
                    acc2.withdraw(amount);
                    acc1.deposit(amount);
                }
                System.out.println("  [SAFE TRANSFER] Completed Rs. " + amount + " between " + acc1.getId() + " and " + acc2.getId() +
                        " | Lock order: " + firstLock.getId() + " -> " + secondLock.getId());
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Deadlock Simulation and Consistent Lock Order Solution ===");

        Account accA = new Account("ACC-101", 5000);
        Account accB = new Account("ACC-202", 5000);

        System.out.println("\nInitial Balances -> ACC-101: " + accA.getBalance() + " | ACC-202: " + accB.getBalance());

        System.out.println("\n--- Running Concurrent Transfers with Consistent Lock Ordering ---");
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                safeTransfer(accA, accB, 100, true);
            }
        }, "Thread-Transfer-A-to-B");

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                safeTransfer(accA, accB, 100, false);
            }
        }, "Thread-Transfer-B-to-A");

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("\nAll concurrent transfers completed without deadlocking!");
        System.out.println("Final Balances -> ACC-101: " + accA.getBalance() + " | ACC-202: " + accB.getBalance());
    }
}
