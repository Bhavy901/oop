package com.minibank.util;

import com.minibank.model.annotation.Audit;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Practical 14 (Part B)
 * Reflective audit logger that intercepts and inspects service layer methods marked with @Audit.
 */
public class AuditLogger {

    private static final List<String> auditTrail = Collections.synchronizedList(new ArrayList<>());
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void logServiceCall(Class<?> serviceClass, String methodName, Object[] args) {
        try {
            for (Method method : serviceClass.getDeclaredMethods()) {
                if (method.getName().equals(methodName) && method.isAnnotationPresent(Audit.class)) {
                    Audit audit = method.getAnnotation(Audit.class);
                    String action = audit.action().isEmpty() ? methodName : audit.action();
                    String timestamp = LocalDateTime.now().format(FORMATTER);
                    String entry = String.format("[%s] AUDIT: %s | Method: %s.%s()",
                            timestamp, action, serviceClass.getSimpleName(), methodName);
                    auditTrail.add(entry);
                    System.out.println("  " + entry);
                    return;
                }
            }
        } catch (Exception ignored) {
        }
    }

    public static List<String> getAuditTrail() {
        return new ArrayList<>(auditTrail);
    }
}
