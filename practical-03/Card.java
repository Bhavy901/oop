import java.util.Objects;

/**
 * Practical 3 - Part A (Problem 2)
 * Duplicate card check overriding toString, equals, and hashCode.
 */
public class Card {

    // (a) Private fields and constructor
    private final String rank;
    private final String suit;

    public Card(String rank, String suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public String getRank() {
        return rank;
    }

    public String getSuit() {
        return suit;
    }

    // (b) Override toString() to return rank + " of " + suit
    @Override
    public String toString() {
        return rank + " of " + suit;
    }

    // (c) Override equals() and hashCode() by rank + suit
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Card card = (Card) obj;
        return Objects.equals(rank, card.rank) && Objects.equals(suit, card.suit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rank, suit);
    }

    // (d) & (e) Driver
    public static void main(String[] args) {
        System.out.println("=== Duplicate Card Check ===");
        Card[] cardStream = {
            new Card("Ace", "Spades"),
            new Card("King", "Hearts"),
            new Card("10", "Diamonds"),
            new Card("Ace", "Spades"), // Duplicate
            new Card("Queen", "Clubs")
        };

        Card[] storage = new Card[cardStream.length];
        int count = 0;

        for (Card newCard : cardStream) {
            System.out.println("Processing card: " + newCard);
            boolean isDuplicate = false;
            for (int i = 0; i < count; i++) {
                if (newCard.equals(storage[i])) {
                    System.out.println("Duplicate found: " + newCard);
                    isDuplicate = true;
                    break;
                }
            }
            if (!isDuplicate) {
                storage[count++] = newCard;
            }
        }
    }
}
