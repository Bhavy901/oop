package com.minibank.util;

public class BankSession implements AutoCloseable {
    private final String sessionUser;
    private final long startTime;
    private boolean active;

    public BankSession(String sessionUser) {
        this.sessionUser = sessionUser;
        this.startTime = System.currentTimeMillis();
        this.active = true;
        System.out.println("[SESSION STARTED] Secure session initialized for: " + sessionUser);
    }

    public void logActivity(String activity) {
        if (!active) {
            throw new IllegalStateException("Session is closed!");
        }
        System.out.println("  [SESSION LOG] " + sessionUser + " -> " + activity);
    }

    @Override
    public void close() {
        if (active) {
            active = false;
            long duration = System.currentTimeMillis() - startTime;
            System.out.println("[SESSION CLOSED] Secure session for " + sessionUser + " closed safely (Duration: " + duration + " ms).");
        }
    }
}
