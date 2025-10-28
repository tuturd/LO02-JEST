package JEST.cards;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck implements Serializable {
    private List<Card> cards;

    private static Deck instance;

    private Deck() {
        this.cards = new ArrayList<>();
    }

    public static Deck getInstance() {
        if (instance == null) {
            instance = new Deck();
        }
        return instance;
    }

    public void fill() {
        try {
            cards.add(new JokerCard());
            cards.add(new SuitCard(Suit.SPADE, 1));
            cards.add(new SuitCard(Suit.SPADE, 2));
            cards.add(new SuitCard(Suit.SPADE, 3));
            cards.add(new SuitCard(Suit.SPADE, 4));
            cards.add(new SuitCard(Suit.CLUB, 1));
            cards.add(new SuitCard(Suit.CLUB, 2));
            cards.add(new SuitCard(Suit.CLUB, 3));
            cards.add(new SuitCard(Suit.CLUB, 4));
            cards.add(new SuitCard(Suit.HEART, 1));
            cards.add(new SuitCard(Suit.HEART, 2));
            cards.add(new SuitCard(Suit.HEART, 3));
            cards.add(new SuitCard(Suit.HEART, 4));
            cards.add(new SuitCard(Suit.DIAMOND, 1));
            cards.add(new SuitCard(Suit.DIAMOND, 2));
            cards.add(new SuitCard(Suit.DIAMOND, 3));
            cards.add(new SuitCard(Suit.DIAMOND, 4));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card deal(int numCards) {
        // Distribuer une carte
        return null; // Remplacer par la carte distribuée
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }
    
    public String toString() {
        System.out.println(cards.size());
        for (Card card : cards) {
            System.out.println(card);
        }
        //return sb.toString();
        return "blabla";
    }
}
