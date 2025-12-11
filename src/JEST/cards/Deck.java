package JEST.cards;

import JEST.cards.trophy.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Map;

/**
 * This class defines the decks : we can shuffle it, fill it...
 */
public class Deck implements Serializable {
    private Queue<Card> cards;
    private static final Map<DeckType, Deck> instances = new HashMap<>();
    
    private Deck() {
        this.cards = new LinkedList<>();
    }
    
    /**
     * Permits to have 2 instances maximum : the general and the restOfCards.
     * @param type : type of the deck (general or restOfCards).
     * @return the deck created.
     */
    public static Deck getInstance(DeckType type) {
        return instances.computeIfAbsent(type, t -> new Deck());
    }
    
    /**
     * Fill the general deck with all the cards of the game. We precise the type of the card (suit or joker), his suit, his value, and the trophy associated to the card.
     */
    public void fill() {
        try {
            this.cards.add(new JokerCard(new BestJestTrophy()));
            this.cards.add(new SuitCard(Suit.SPADE, 1, new HighestInSuitTrophy(Suit.CLUB)));
            this.cards.add(new SuitCard(Suit.SPADE, 2, new LowestInSuitTrophy(Suit.HEART)));
            this.cards.add(new SuitCard(Suit.SPADE, 3, new HighestInSuitTrophy(Suit.HEART)));
            this.cards.add(new SuitCard(Suit.SPADE, 4, new LowestInSuitTrophy(Suit.CLUB)));
            this.cards.add(new SuitCard(Suit.CLUB, 1, new HighestInSuitTrophy(Suit.SPADE)));
            this.cards.add(new SuitCard(Suit.CLUB, 2, new MajorityFaceValueTrophy(3)));
            this.cards.add(new SuitCard(Suit.CLUB, 3, new MajorityFaceValueTrophy(2)));
            this.cards.add(new SuitCard(Suit.CLUB, 4, new LowestInSuitTrophy(Suit.SPADE)));
            this.cards.add(new SuitCard(Suit.HEART, 1, new JokerTrophy()));
            this.cards.add(new SuitCard(Suit.HEART, 2, new JokerTrophy()));
            this.cards.add(new SuitCard(Suit.HEART, 3, new JokerTrophy()));
            this.cards.add(new SuitCard(Suit.HEART, 4, new JokerTrophy()));
            this.cards.add(new SuitCard(Suit.DIAMOND, 1, new MajorityFaceValueTrophy(3)));
            this.cards.add(new SuitCard(Suit.DIAMOND, 2, new HighestInSuitTrophy(Suit.DIAMOND)));
            this.cards.add(new SuitCard(Suit.DIAMOND, 3, new LowestInSuitTrophy(Suit.DIAMOND)));
            this.cards.add(new SuitCard(Suit.DIAMOND, 4, new BestJestWithoutJokerTrophy()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Shuffle the deck.
     */
    public void shuffle() {
        List<Card> cardsList = new ArrayList<>(this.cards);
        Collections.shuffle(cardsList);
        this.cards = new LinkedList<>(cardsList);
    }
    
    /**
     * We deal a card and return it.
     * @return the card dealt.
     */
    public Card deal() {
        return this.cards.poll();
    }
    
    /**
     * Deal the number of cards asked.
     * @param numCards : the number of cards who have to be dealt.
     * @return the list of the cards dealt.
     */
    public List<Card> deal(int numCards) {
        List<Card> cards = new ArrayList<>(numCards);

        for (int i = 0; i < numCards; i++) {
            cards.add(this.cards.poll());
        }

        return cards;
    }
    
    public void add(Card card) {
        this.cards.add(card);
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
