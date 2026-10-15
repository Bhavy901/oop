import java.util.*;

/**
 * Practical 12 - Part A (Problem 3 - For Advanced Learners – home)
 * Gaming Leaderboard ranking top players with custom tie-breaking Comparator.
 */
public class Leaderboard {

    public record PlayerScore(String playerName, int score) {}

    public static List<PlayerScore> getTopN(Map<String, Integer> playerScores, int n) {
        List<PlayerScore> list = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : playerScores.entrySet()) {
            list.add(new PlayerScore(entry.getKey(), entry.getValue()));
        }

        // Sort descending by score, tie-break ascending by player name
        list.sort((p1, p2) -> {
            int scoreCompare = Integer.compare(p2.score(), p1.score());
            if (scoreCompare != 0) {
                return scoreCompare;
            }
            return p1.playerName().compareToIgnoreCase(p2.playerName());
        });

        int limit = Math.min(n, list.size());
        return list.subList(0, limit);
    }

    public static void main(String[] args) {
        System.out.println("=== Competitive Gaming Leaderboard ===");

        Map<String, Integer> scores = new HashMap<>();
        scores.put("ShadowNinja", 2850);
        scores.put("PixelKnight", 3100);
        scores.put("ViperStrike", 2850); // Tie with ShadowNinja
        scores.put("AlphaWolf", 1950);
        scores.put("CyberMage", 3450);
        scores.put("ThunderBolt", 2400);
        scores.put("BlazeFire", 3100);   // Tie with PixelKnight

        System.out.println("Raw Player Scores Register (" + scores.size() + " active players):\n" + scores);

        int topCount = 5;
        System.out.println("\nTop " + topCount + " Leaderboard Standings (Ties broken alphabetically by name):");
        System.out.printf("%-6s | %-15s | %-6s%n", "Rank", "Player", "Score");
        System.out.println("---------------------------------");

        List<PlayerScore> topPlayers = getTopN(scores, topCount);
        int rank = 1;
        for (PlayerScore p : topPlayers) {
            System.out.printf("#%-5d | %-15s | %6d pts%n", rank++, p.playerName(), p.score());
        }
    }
}
