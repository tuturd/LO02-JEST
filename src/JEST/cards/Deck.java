package JEST.cards;

import java.util.List;

public class Deck {
    private List<Card> cards;

    private static Deck instance;

    private Deck() {
        // Initialiser le deck avec les cartes
    }

    public static Deck getInstance() {
        if (instance == null) {
            instance = new Deck();
        }
        return instance;
    }

    public void shuffle() {
        // Mélanger les cartes
    }

    public Card deal(int numCards) {
        // Distribuer une carte
        return null; // Remplacer par la carte distribuée
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }
}
