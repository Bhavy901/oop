import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Practical 7 - Part A (Problem 3 - For Advanced Learners – home)
 * Object-Relational / CSV column mapping using @Column reflection.
 */
public class ColumnMapper {

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface Column {
        String name();
        boolean required() default false;
    }

    public static class StudentRecord {
        @Column(name = "student_id", required = true)
        private String studentId;

        @Column(name = "full_name")
        private String fullName;

        @Column(name = "marks")
        private double marks;

        @Column(name = "city")
        private String city;

        public StudentRecord() {}

        @Override
        public String toString() {
            return "StudentRecord{studentId='" + studentId + "', fullName='" + fullName + "', marks=" + marks + ", city='" + city + "'}";
        }
    }

    public static <T> T mapRow(Class<T> clazz, String[] headers, String[] rowData) {
        try {
            T instance = clazz.getDeclaredConstructor().newInstance();

            // Index map for headers
            Map<String, Integer> headerIndexMap = new HashMap<>();
            for (int i = 0; i < headers.length; i++) {
                headerIndexMap.put(headers[i].trim().toLowerCase(), i);
            }

            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(Column.class)) {
                    Column col = field.getAnnotation(Column.class);
                    String colName = col.name().trim().toLowerCase();

                    field.setAccessible(true);
                    if (headerIndexMap.containsKey(colName)) {
                        int index = headerIndexMap.get(colName);
                        if (index < rowData.length && rowData[index] != null) {
                            String rawValue = rowData[index].trim();
                            setFieldValue(instance, field, rawValue);
                        }
                    } else {
                        // Decide how to handle missing column:
                        if (col.required()) {
                            System.err.println("WARNING: Required column '" + col.name() + "' was not found in headers!");
                        } else {
                            System.out.println("Notice: Optional column '" + col.name() + "' missing in input, defaulted to null/zero.");
                        }
                    }
                }
            }
            return instance;
        } catch (Exception e) {
            throw new RuntimeException("Failed to map row to " + clazz.getSimpleName(), e);
        }
    }

    private static void setFieldValue(Object instance, Field field, String value) throws IllegalAccessException {
        Class<?> type = field.getType();
        if (type == String.class) {
            field.set(instance, value);
        } else if (type == int.class || type == Integer.class) {
            field.set(instance, Integer.parseInt(value));
        } else if (type == double.class || type == Double.class) {
            field.set(instance, Double.parseDouble(value));
        } else if (type == long.class || type == Long.class) {
            field.set(instance, Long.parseLong(value));
        } else if (type == boolean.class || type == Boolean.class) {
            field.set(instance, Boolean.parseBoolean(value));
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Reflection @Column Table Mapper ===");

        String[] headers = {"student_id", "full_name", "marks"};
        String[] row1 = {"25CS016", "Bhavy Gol", "98.5"};
        String[] row2 = {"25CS042", "Aarav Shah", "92.0"};

        System.out.println("Headers: [student_id, full_name, marks]  (Note: 'city' column is absent in data)");

        System.out.println("\nMapping Row 1:");
        StudentRecord student1 = mapRow(StudentRecord.class, headers, row1);
        System.out.println("Result: " + student1);

        System.out.println("\nMapping Row 2:");
        StudentRecord student2 = mapRow(StudentRecord.class, headers, row2);
        System.out.println("Result: " + student2);
    }
}
