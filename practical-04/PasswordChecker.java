import java.util.regex.Pattern;

public class PasswordChecker {

    private static final Pattern UPPERCASE_PATTERN = Pattern.compile(".*[A-Z].*");
    private static final Pattern DIGIT_PATTERN = Pattern.compile(".*[0-9].*");
    private static final Pattern SPECIAL_PATTERN = Pattern.compile(".*[^a-zA-Z0-9].*");

    public static String strength(String pw) {
        if (pw == null) return "Weak";
        int passedRules = countPassedRules(pw);
        if (passedRules <= 1) {
            return "Weak";
        } else if (passedRules <= 3) {
            return "Medium";
        } else {
            return "Strong";
        }
    }

    public static int countPassedRules(String pw) {
        int count = 0;
        if (pw.length() >= 8) count++;
        if (UPPERCASE_PATTERN.matcher(pw).matches()) count++;
        if (DIGIT_PATTERN.matcher(pw).matches()) count++;
        if (SPECIAL_PATTERN.matcher(pw).matches()) count++;
        return count;
    }

    public static void printRuleDetails(String pw) {
        boolean r1 = pw.length() >= 8;
        boolean r2 = UPPERCASE_PATTERN.matcher(pw).matches();
        boolean r3 = DIGIT_PATTERN.matcher(pw).matches();
        boolean r4 = SPECIAL_PATTERN.matcher(pw).matches();

        System.out.println("Password: \"" + pw + "\"");
        System.out.println("  - Length >= 8: " + (r1 ? "PASS" : "FAIL"));
        System.out.println("  - Uppercase letter: " + (r2 ? "PASS" : "FAIL"));
        System.out.println("  - Digit: " + (r3 ? "PASS" : "FAIL"));
        System.out.println("  - Special character: " + (r4 ? "PASS" : "FAIL"));
        System.out.println("  -> Strength: " + strength(pw) + "\n");
    }

    public static void main(String[] args) {
        System.out.println("=== Password Strength Evaluator ===");
        String[] testPasswords = {
            "abc",
            "password",
            "Pass123",
            "Abcd1234!",
            "Complex#2026_Secure"
        };

        for (String pw : testPasswords) {
            printRuleDetails(pw);
        }
    }
}
