import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Practical 4 - Part A (Problem 3 - For Advanced Learners – home)
 * Template filler substituting placeholders using Pattern, Matcher, and StringBuilder.
 */
public class TemplateFiller {

    public static void main(String[] args) {
        System.out.println("=== Template Filler Evaluation ===");

        // (a) Template string and parallel arrays
        String template = "Dear {name}, order {id} ships {date}.";
        String[] names = {"name", "id"};
        String[] values = {"Riya", "A07"};
        // Note: {date} is not supplied

        System.out.println("Original Template: " + template);

        // (b) Compile Pattern for \{(\w+)\}
        Pattern pattern = Pattern.compile("\\{(\\w+)\\}");
        Matcher matcher = pattern.matcher(template);

        // (c) Walk matches with StringBuilder
        StringBuilder filled = new StringBuilder();
        while (matcher.find()) {
            String key = matcher.group(1);
            String replacement = "[?]"; // default if not found

            for (int i = 0; i < names.length; i++) {
                if (names[i].equals(key)) {
                    replacement = values[i];
                    break;
                }
            }

            matcher.appendReplacement(filled, Matcher.quoteReplacement(replacement));
        }
        matcher.appendTail(filled);

        // (d) Print filled template
        System.out.println("Filled Template:   " + filled.toString());
    }
}
