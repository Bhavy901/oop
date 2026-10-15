import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class FormValidator {

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface NotBlank {
        String message() default "Field cannot be empty or blank";
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface MaxLength {
        int value();
        String message() default "Length exceeds maximum allowed limit";
    }

    public static class SignupForm {
        @NotBlank(message = "Username is mandatory")
        @MaxLength(value = 10, message = "Username must not exceed 10 characters")
        private String username;

        @NotBlank(message = "Email is mandatory")
        private String email;

        @NotBlank(message = "Password cannot be blank")
        @MaxLength(value = 16, message = "Password must not exceed 16 characters")
        private String password;

        public SignupForm(String username, String email, String password) {
            this.username = username;
            this.email = email;
            this.password = password;
        }
    }

    public static List<String> validate(Object obj) {
        List<String> errors = new ArrayList<>();
        if (obj == null) {
            errors.add("Validated object is null");
            return errors;
        }

        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object val = field.get(obj);
                String strVal = (val == null) ? null : val.toString();

                if (field.isAnnotationPresent(NotBlank.class)) {
                    NotBlank notBlank = field.getAnnotation(NotBlank.class);
                    if (strVal == null || strVal.trim().isEmpty()) {
                        errors.add(field.getName() + ": " + notBlank.message());
                    }
                }

                if (field.isAnnotationPresent(MaxLength.class)) {
                    MaxLength maxLength = field.getAnnotation(MaxLength.class);
                    if (strVal != null && strVal.length() > maxLength.value()) {
                        errors.add(field.getName() + ": " + maxLength.message() + " (Max: " + maxLength.value() + ", Actual: " + strVal.length() + ")");
                    }
                }
            } catch (IllegalAccessException e) {
                errors.add("Error accessing field " + field.getName() + ": " + e.getMessage());
            }
        }
        return errors;
    }

    public static void main(String[] args) {
        System.out.println("=== Reflection Form Validator ===");

        SignupForm validForm = new SignupForm("bhavy_gol", "bhavy@charusat.edu.in", "Pass1234");
        SignupForm invalidForm = new SignupForm("extremely_long_username_that_fails", "   ", "very_long_password_that_exceeds_the_max_allowed_limit_12345");

        System.out.println("1. Validating Valid Form:");
        List<String> errors1 = validate(validForm);
        if (errors1.isEmpty()) {
            System.out.println("  Form is 100% Valid! No errors found.\n");
        } else {
            errors1.forEach(e -> System.out.println("  - " + e));
        }

        System.out.println("2. Validating Invalid Form:");
        List<String> errors2 = validate(invalidForm);
        System.out.println("  Found " + errors2.size() + " validation error(s):");
        errors2.forEach(e -> System.out.println("  [ERROR] " + e));
    }
}
