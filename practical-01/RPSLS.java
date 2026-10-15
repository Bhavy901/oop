import java.util.Random;
import java.util.Scanner;

/**
 * Practical 1 - Part A (Problem 3 - For Advanced Learners – home)
 * Rock-Paper-Scissors-Lizard-Spock using enum and switch expressions.
 */
public class RPSLS {

    // (a) Define an enum Move with ROCK, PAPER, SCISSORS, LIZARD, SPOCK.
    public enum Move {
        ROCK, PAPER, SCISSORS, LIZARD, SPOCK
    }

    /**
     * (b) Returns:
     *  1 if a beats b
     * -1 if b beats a
     *  0 for a tie
     *
     * Rules:
     * - Scissors cut Paper
     * - Paper covers Rock
     * - Rock crushes Lizard
     * - Lizard poisons Spock
     * - Spock smashes Scissors
     * - Scissors decapitates Lizard
     * - Lizard eats Paper
     * - Paper disproves Spock
     * - Spock vaporizes Rock
     * - Rock crushes Scissors
     */
    public static int winner(Move a, Move b) {
        if (a == b) {
            return 0;
        }

        boolean aBeatsB = switch (a) {
            case SCISSORS -> (b == Move.PAPER || b == Move.LIZARD);
            case PAPER -> (b == Move.ROCK || b == Move.SPOCK);
            case ROCK -> (b == Move.LIZARD || b == Move.SCISSORS);
            case LIZARD -> (b == Move.SPOCK || b == Move.PAPER);
            case SPOCK -> (b == Move.SCISSORS || b == Move.ROCK);
        };

        return aBeatsB ? 1 : -1;
    }

    public static void main(String[] args) {
        System.out.println("=== Rock-Paper-Scissors-Lizard-Spock (5 Rounds) ===");
        Move[] allMoves = Move.values();
        Random random = new Random();
        Scanner scanner = new Scanner(System.in);

        int userScore = 0;
        int computerScore = 0;

        for (int round = 1; round <= 5; round++) {
            System.out.println("\n--- Round " + round + " of 5 ---");
            Move computerMove = allMoves[random.nextInt(allMoves.length)];

            Move userMove = null;
            while (userMove == null) {
                System.out.print("Enter your move (ROCK, PAPER, SCISSORS, LIZARD, SPOCK): ");
                if (!scanner.hasNext()) {
                    // Fallback in case of automated test or EOF
                    userMove = allMoves[random.nextInt(allMoves.length)];
                    System.out.println(userMove);
                    break;
                }
                String input = scanner.next().trim().toUpperCase();
                try {
                    userMove = Move.valueOf(input);
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid move! Valid options: ROCK, PAPER, SCISSORS, LIZARD, SPOCK");
                }
            }

            int result = winner(userMove, computerMove);
            System.out.println("Player chose: " + userMove + " | Computer chose: " + computerMove);

            if (result == 1) {
                userScore++;
                System.out.println("Round Winner: Player! (" + userMove + " beats " + computerMove + ")");
            } else if (result == -1) {
                computerScore++;
                System.out.println("Round Winner: Computer! (" + computerMove + " beats " + userMove + ")");
            } else {
                System.out.println("Round Winner: It's a Tie!");
            }
            System.out.println("Current Score -> Player: " + userScore + " | Computer: " + computerScore);
        }

        // (d) Print overall winner
        System.out.println("\n=== Game Summary ===");
        if (userScore > computerScore) {
            System.out.println("You win " + userScore + "–" + computerScore);
        } else if (computerScore > userScore) {
            System.out.println("Computer wins " + computerScore + "–" + userScore);
        } else {
            System.out.println("It's a draw " + userScore + "–" + computerScore);
        }
        scanner.close();
    }
}
