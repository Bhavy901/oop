public class AutoCloseableResourceDemo {

    public static class SecureDatabaseSession implements AutoCloseable {
        private final String sessionId;
        private boolean isOpen;

        public SecureDatabaseSession(String sessionId) {
            this.sessionId = sessionId;
            this.isOpen = true;
            System.out.println("[SESSION OPENED] Secure DB session established with ID: " + sessionId);
        }

        public void executeQuery(String sql) throws Exception {
            if (!isOpen) {
                throw new IllegalStateException("Session is already closed!");
            }
            if (sql.contains("CORRUPT")) {
                throw new RuntimeException("Fatal SQL Query Execution Error: Syntax violation or disk fault in '" + sql + "'");
            }
            System.out.println("  [QUERY EXECUTED] Successfully ran: " + sql);
        }

        @Override
        public void close() {
            if (isOpen) {
                isOpen = false;
                System.out.println("[SESSION CLOSED] Connection " + sessionId + " safely released back to connection pool.");
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Try-With-Resources Deterministic Close Demonstration ===\n");

        System.out.println("1. Scenario A: Successful operation without exceptions");
        try (SecureDatabaseSession session = new SecureDatabaseSession("SESSION-ALPHA")) {
            session.executeQuery("SELECT * FROM users WHERE active = 1");
        } catch (Exception e) {
            System.out.println("Caught unexpected: " + e.getMessage());
        }

        System.out.println("\n---------------------------------------------------------");
        System.out.println("2. Scenario B: Exception occurs inside try block (demonstrating cleanup & error preservation)");
        try (SecureDatabaseSession session = new SecureDatabaseSession("SESSION-BETA")) {
            session.executeQuery("UPDATE accounts SET balance = balance + 100");
            session.executeQuery("SELECT * FROM CORRUPT_TABLE");
            session.executeQuery("UNREACHABLE CODE");
        } catch (Exception e) {
            System.out.println("\n[OUTER CATCH BLOCK] Caught primary exception:");
            System.out.println("  Exception Type: " + e.getClass().getName());
            System.out.println("  Message:        " + e.getMessage());

            Throwable[] suppressed = e.getSuppressed();
            if (suppressed.length > 0) {
                System.out.println("  Suppressed exceptions during close: " + suppressed.length);
            }
        }

        System.out.println("\nProof: In both cases, the session closed automatically before leaving the try block!");
    }
}
