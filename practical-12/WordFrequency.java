import java.util.*;

public class WordFrequency {

    public static void main(String[] args) {
        System.out.println("=== Word Frequency Counter ===");

        String paragraph = "Object oriented programming with Java is powerful. Java provides robust collections, "
                + "and Java makes object oriented design clean, scalable, and object safe.";

        System.out.println("Input Paragraph:\n\"" + paragraph + "\"\n");

        String cleaned = paragraph.replaceAll("[^a-zA-Z0-9\\s]", "").toLowerCase();
        String[] words = cleaned.split("\\s+");

        Map<String, Integer> freqMap = new HashMap<>();
        for (String word : words) {
            if (!word.isEmpty()) {
                freqMap.put(word, freqMap.getOrDefault(word, 0) + 1);
            }
        }

        List<Map.Entry<String, Integer>> entryList = new ArrayList<>(freqMap.entrySet());

        Comparator<Map.Entry<String, Integer>> frequencyComparator = (e1, e2) -> {
            int cmp = e2.getValue().compareTo(e1.getValue());
            if (cmp != 0) {
                return cmp;
            }
            return e1.getKey().compareTo(e2.getKey());
        };

        entryList.sort(frequencyComparator);

        System.out.println("Word Frequencies (Sorted Highest to Lowest):");
        System.out.printf("%-15s | %s%n", "Word", "Frequency");
        System.out.println("-------------------------");
        for (Map.Entry<String, Integer> entry : entryList) {
            System.out.printf("%-15s | %d%n", entry.getKey(), entry.getValue());
        }
    }
}
