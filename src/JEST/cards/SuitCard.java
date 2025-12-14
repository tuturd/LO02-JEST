package JEST.cards;

import JEST.cards.trophy.Trophy;

/**
 * It defines a {@link Card} with a {@link Suit}.
 */
public class SuitCard extends Card {

    private final Suit suit;
    private int faceValue;
    private static final long serialVersionUID = 1L;

    /**
     * A suitcard is defined by its {@link Suit}, its faceValue and its {@link Trophy}.
     * @param suit
     * @param faceValue
     * @param trophy
     */
    public SuitCard(Suit suit, int faceValue, Trophy trophy) {
        super(trophy);
        this.suit = suit;
        this.faceValue = faceValue;
    }

    public int getFaceValue() {
        return this.faceValue;
    }

    public Suit getSuit() {
        return this.suit;
    }

    /**
     * Accept the visitor.
     */
    public void accept(CardVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * Transform the face value into 5.
     */
    public void transformToFive() {
        this.faceValue = 5;
    }
}

