package JEST.cards;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Map;

public class Deck implements Serializable {
    private Queue<Card> cards;
    
    private static final Map<DeckType, Deck> instances = new HashMap<>();
    
    private Deck() {
        this.cards = new LinkedList<>();
    }
    
    public static synchronized Deck getInstance(DeckType type) {
        return instances.computeIfAbsent(type, t -> new Deck());
    }

    public void fill() {
        try {
            this.cards.add(new JokerCard());
            this.cards.add(new SuitCard(Suit.SPADE, 1));
            this.cards.add(new SuitCard(Suit.SPADE, 2));
            this.cards.add(new SuitCard(Suit.SPADE, 3));
            this.cards.add(new SuitCard(Suit.SPADE, 4));
            this.cards.add(new SuitCard(Suit.CLUB, 1));
            this.cards.add(new SuitCard(Suit.CLUB, 2));
            this.cards.add(new SuitCard(Suit.CLUB, 3));
            this.cards.add(new SuitCard(Suit.CLUB, 4));
            this.cards.add(new SuitCard(Suit.HEART, 1));
            this.cards.add(new SuitCard(Suit.HEART, 2));
            this.cards.add(new SuitCard(Suit.HEART, 3));
            this.cards.add(new SuitCard(Suit.HEART, 4));
            this.cards.add(new SuitCard(Suit.DIAMOND, 1));
            this.cards.add(new SuitCard(Suit.DIAMOND, 2));
            this.cards.add(new SuitCard(Suit.DIAMOND, 3));
            this.cards.add(new SuitCard(Suit.DIAMOND, 4));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void shuffle() {
        List<Card> cardsList = new ArrayList<>(this.cards);
        Collections.shuffle(cardsList);
        this.cards = new LinkedList<>(cardsList);
    }

    public Card deal(int numCards) {
        return this.cards.poll();
    }

    public boolean isEmpty() {
        return this.cards.isEmpty();
    }
    
    public String toString() {
        System.out.println(this.cards.size());
        for (Card card : this.cards) {
            System.out.println(card);
        }
        return null;
    }
}
